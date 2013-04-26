/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.tools.internal;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMWriter {

	static String ENCONDING = "UTF8";
	PrintStream out;
	String[] attributeFilter;
	String[] idAttributes;
	String nodeFilter;

	public DOMWriter(PrintStream out) {
		this.out = new PrintStream(out);
	}

	String nodeName(Node node) {
		// TODO use getLocalName()?
		return node.getNodeName();
	}
	
	boolean filter(Attr attr) {
		if (attributeFilter == null) return false;
		String name = attr.getNodeName();
		for (int i = 0; i < attributeFilter.length; i++) {
			if (name.matches(attributeFilter[i])) return false;
		}
		return true;
	}
	
	Node getIDAttribute(Node node) {
		NamedNodeMap attributes = node.getAttributes();
		if (attributes == null) return null;
		String[] names = idAttributes;
		for (int i = 0; i < names.length; i++) {
			Node nameAttrib = attributes.getNamedItem(names[i]);
			if (nameAttrib != null) return nameAttrib;
		}
		return null;
	}
	
	void print(String str) {
		out.print(str);
	}
	void println() {
		out.println();
	}

	public void print(Node node) {
		print(node, 0);
	}
	
	public void print(Node node, int level) {
		if (node == null)
			return;
		int type = node.getNodeType();
		switch (type) {
			case Node.DOCUMENT_NODE: {
				print("<?xml version=\"1.0\" encoding=\"");
				print(ENCONDING);
				print("\"?>");
				println();
				print(((Document) node).getDocumentElement());
				break;
			}
			case Node.ELEMENT_NODE: {
				Attr attrs[] = sort(node.getAttributes());
				String name = nodeName(node);
				boolean isArg = name.equals("arg");
				boolean gen = isArg || name.equals("retval");
				for (int i = 0; i < attrs.length && !gen; i++) {
					Attr attr = attrs[i];
					if (nodeName(attr).startsWith(nodeFilter)) gen = true;
				}
				if (!gen) break;
				for (int i = 0; i < level; i++) print("\t");
				print("<");
				print(name);
				for (int i = 0; i < attrs.length; i++) {
					Attr attr = attrs[i];
					if (isArg && "name".equals(attr.getNodeName())) continue;
					if (filter(attr)) continue;
					print(" ");
					print(nodeName(attr));
					print("=\"");
					print(normalize(attr.getNodeValue()));
					print("\"");
				}
				print(">");
				NodeList children = node.getChildNodes();
				if (children != null) {
					int len = children.getLength();
					ArrayList nodes = new ArrayList();
					for (int i = 0; i < len; i++) {
						Node child = children.item(i);
						if (child.getNodeType() == Node.ELEMENT_NODE) nodes.add(child);
					}
					int count = nodes.size();
					Collections.sort(nodes, new Comparator() {
						public int compare(Object arg0, Object arg1) {
							Node a = (Node)arg0;
							Node b = (Node)arg1;
							String nameA = a.getNodeName();
							String nameB = b.getNodeName();
							if ("arg".equals(nameA)) {
								return 0;
							} else {
								int result = nameA.compareTo(nameB);
								if (result == 0) {
									Node idA = getIDAttribute(a);
									Node idB = getIDAttribute(b);
									if (idA == null || idB == null) return 0;
									return idA.getNodeValue().compareTo(idB.getNodeValue());
								}
								return result;
							}
						}
					});
					if (count > 0) println();
					for (int i = 0; i < count; i++) {
						print((Node)nodes.get(i), level + 1);
					}
					if (count > 0) {
						for (int i = 0; i < level; i++) print("\t");
					}
				}
				print("</");
				print(nodeName(node));
				print(">");
				println();
				break;
			}
		}
		out.flush();
	}

	Attr[] sort(NamedNodeMap attrs) {
		if (attrs == null)
			return new Attr[0];
		Attr result[] = new Attr[attrs.getLength()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (Attr) attrs.item(i);
		}
		Arrays.sort(result, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				return nodeName((Node) arg0).compareTo(nodeName((Node) arg1));
			}
		});
		return result;
	}

	String normalize(String s) {
		if (s == null) return "";
		StringBuffer str = new StringBuffer();
		for (int i = 0, length = s.length(); i < length; i++) {
			char ch = s.charAt(i);
			switch (ch) {
				case '"': str.append("\""); break;
				case '\r':
				case '\n':
					// FALL THROUGH
				default: str.append(ch);
			}
		}
		return str.toString();
	}
	
	public void setNodeFilter(String filter) {
		
		nodeFilter = filter;
	}
	
	public void setAttributeFilter(String[] filter) {
		attributeFilter = filter;
	}
	
	public void setIDAttributes(String[] ids) {
		idAttributes = ids;
	}
}