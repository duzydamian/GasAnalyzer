/*******************************************************************************
 * Copyright (c) 2007, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal.cocoa;

import org.eclipse.swt.internal.*;

public class OS extends C {
	static {
		Library.loadLibrary("swt-pi"); //$NON-NLS-1$
	}
	
	public static final int VERSION;
	static {
		int [] response = new int [1];
		OS.Gestalt (OS.gestaltSystemVersion, response);
		VERSION = response [0] & 0xffff;		
	}
	
	/*
	 *  Magic number explanation, from Cocoa's TextSizingExample:
	 *  
	 *  "The first is called LargeNumberForText [1.0e7] and it was not arbitrarily chosen.
	 *  The actual value was chosen to be around the largest floating point value possible that can preserve at least pixel precision. [...]
	 *  It is not wise to use bigger dimensions for text system objects because, even if you ever fill all that space, 
	 *  by the time you get to the far reaches, the letters won't have the precision necessary to look and act correctly. 
	 *  [...] Because the Cocoa text system respects this limit in various ways, a second constant, NotQuiteAsLargeNumberForText, is used for the 
	 *  field-like text views created by the FieldAspect class. This is simply half of LargeNumberForText; at sizes as large as LargeNumberForText, 
	 *  the text system stops aligning text, for various reasons."
	 */
	public static final double /*float*/ MAX_TEXT_CONTAINER_SIZE = 0.5e7f;
	
	public static final int gestaltSystemVersion = ('s'<<24) + ('y'<<16) + ('s'<<8) + 'v';
	public static final int noErr = 0;
	public static final int kProcessTransformToForegroundApplication = 1;
	public static final int kSystemIconsCreator = ('m' << 24) + ('a' << 16) + ('c' << 8) + 's';
	public static final int kAlertCautionIcon = ('c'<<24) + ('a'<<16) + ('u'<<8) + 't';
	public static final int kAlertNoteIcon = ('n'<<24) + ('o'<<16) + ('t'<<8) + 'e';
	public static final int kAlertStopIcon = ('s'<<24) + ('t'<<16) + ('o'<<8) + 'p';
	public static final int kHICommandHide = ('h'<<24) + ('i'<<16) + ('d'<<8) + 'e';
	public static final int kHICommandHideOthers = ('h'<<24) + ('i'<<16) + ('d'<<8) + 'o';
	public static final int kHICommandShowAll = ('s'<<24) + ('h'<<16) + ('a'<<8) + 'l';
	public static final int kHICommandQuit = ('q'<<24) + ('u'<<16) + ('i'<<8) + 't';
	public static final int kHICommandServices = ('s'<<24) + ('e'<<16) + ('r'<<8) + 'v';
	public static final int shiftKey = 1 << 9;
	public static final int kThemeMetricFocusRectOutset = 7;
	public static final int kHIThemeOrientationNormal = 0;
	public static final int kUIModeNormal = 0;
	public static final int kUIModeContentHidden = 2;
	public static final int kUIModeAllHidden = 3;
	public static final int kLSUnknownType = 0;
	public static final int kLSUnknownCreator = 0;
	public static final int kLSRolesAll = 0xFFFFFFFF;
	public static final int kAXUnderlineStyleNone = 0x0;
	public static final int kAXUnderlineStyleSingle = 0x1; 
	public static final int kAXUnderlineStyleThick = 0x2; 
	public static final int kAXUnderlineStyleDouble = 0x9;
	public static final int kPMDestinationPrinter = 1;
	public static final int kPMDuplexNone = 0x0001;
	public static final int kPMDuplexNoTumble = 0x0002;
	public static final int kPMDuplexTumble = 0x0003;

	public static final long /*int*/ sel_identity = sel_registerName("identity");
	public static final long /*int*/ sel_sendSearchSelection = sel_registerName("sendSearchSelection");
	public static final long /*int*/ sel_sendCancelSelection = sel_registerName("sendCancelSelection");
	public static final long /*int*/ sel_sendSelection = sel_registerName("sendSelection");
	public static final long /*int*/ sel_sendSelection_ = sel_registerName("sendSelection:");
	public static final long /*int*/ sel_sendDoubleSelection = sel_registerName("sendDoubleSelection");
	public static final long /*int*/ sel_sendVerticalSelection = sel_registerName("sendVerticalSelection");
	public static final long /*int*/ sel_sendHorizontalSelection = sel_registerName("sendHorizontalSelection");
	public static final long /*int*/ sel_timerProc_ = sel_registerName("timerProc:");
	public static final long /*int*/ sel_handleNotification_ = sel_registerName("handleNotification:");
	public static final long /*int*/ sel_callJava = sel_registerName("callJava:index:token:arg:");
	public static final long /*int*/ sel_callRunBeforeUnloadConfirmPanelWithMessage = sel_registerName("callRunBeforeUnloadConfirmPanelWithMessage:arg:");
	public static final long /*int*/ sel_createPanelDidEnd = sel_registerName("createPanelDidEnd:returnCode:contextInfo:");
	public static final long /*int*/ sel_systemSettingsChanged_ = sel_registerName("systemSettingsChanged:");
	public static final long /*int*/ sel_panelDidEnd_returnCode_contextInfo_ = sel_registerName("panelDidEnd:returnCode:contextInfo:");
	public static final long /*int*/ sel_updateOpenGLContext_ = sel_registerName("updateOpenGLContext:");
	
	public static final long /*int*/ sel_overwriteExistingFileCheck = sel_registerName("_overwriteExistingFileCheck:");
	public static final long /*int*/ sel_setShowsHiddenFiles_ = sel_registerName("setShowsHiddenFiles:");
	
	public static final long /*int*/ sel_setMovable_ = OS.sel_registerName("setMovable:");

	public static final long /*int*/ sel_contextID = OS.sel_registerName("contextID");

	public static final long /*int*/ sel__drawThemeProgressArea_ = OS.sel_registerName("_drawThemeProgressArea:");
	
	public static final long /*int*/ sel__setDashboardBehavior = OS.sel_registerName("_setDashboardBehavior:to:");

	public static final long /*int*/ sel__setNeedsToUseHeartBeatWindow_ = OS.sel_registerName("_setNeedsToUseHeartBeatWindow:");

	public static final long /*int*/ class_WebPanelAuthenticationHandler = OS.objc_getClass("WebPanelAuthenticationHandler");
	public static final long /*int*/ sel_sharedHandler = sel_registerName("sharedHandler");
	public static final long /*int*/ sel_startAuthentication = sel_registerName("startAuthentication:window:");
	public static final long /*int*/ sel_setAllowsAnyHTTPSCertificate = sel_registerName("setAllowsAnyHTTPSCertificate:forHost:");
	
	public static final long /*int*/ sel_accessibleHandle = sel_registerName("accessibleHandle");
	public static final long /*int*/ sel_getImageView = sel_registerName("getImageView");

	public static final long /*int*/ sel_clearDeferFlushing = sel_registerName("clearDeferFlushing");
	
	public static final long /*int*/ sel_setShouldExpandItem_ = sel_registerName("setShouldExpandItem:");
	public static final long /*int*/ sel_setShouldScrollClipView_ = sel_registerName("setShouldScrollClipView:");
	
	/* These are not generated in order to avoid creating static methods on all classes */
	public static final long /*int*/ sel_isSelectorExcludedFromWebScript_ = sel_registerName("isSelectorExcludedFromWebScript:");
	public static final long /*int*/ sel_webScriptNameForSelector_ = sel_registerName("webScriptNameForSelector:");
	
	public static final long /*int*/ sel_setColor_forAttribute_ = sel_registerName("setColor:forAttribute:");
	
	public static final long /*int*/ sel_javaRunLoopMode = sel_registerName("javaRunLoopMode");

	/* These are not generated in order to avoid attempting to create a java method called "null" */
	public static final long /*int*/ class_NSNull = objc_getClass("NSNull");
	public static final long /*int*/ sel_null = sel_registerName("null");

	/* NSTextAttachmentCell */
	/** @method callback_types=NSPoint;id;SEL;,callback_flags=struct;none;none; */
	public static final native long /*int*/ CALLBACK_cellBaselineOffset(long /*int*/ func);
	/** @method callback_types=NSSize;id;SEL;,callback_flags=struct;none;none; */
	public static final native long /*int*/ CALLBACK_NSTextAttachmentCell_cellSize(long /*int*/ func);
	public static final long /*int*/ protocol_NSTextAttachmentCell = objc_getProtocol("NSTextAttachmentCell");
	public static final long /*int*/ sel_cellBaselineOffset = sel_registerName("cellBaselineOffset");
	
	/*10.6 Accessibility Strings*/
	/** @method flags=const dynamic no_gen*/
	public static final native long /*int*/ NSAccessibilityRowIndexRangeAttribute();
	public static final NSString NSAccessibilityRowIndexRangeAttribute = new NSString(NSAccessibilityRowIndexRangeAttribute());
	/** @method flags=const dynamic no_gen*/
	public static final native long /*int*/ NSAccessibilityColumnIndexRangeAttribute();
	public static final NSString NSAccessibilityColumnIndexRangeAttribute = new NSString(NSAccessibilityColumnIndexRangeAttribute());
	/** @method flags=const dynamic no_gen*/
	public static final native long /*int*/ NSAccessibilityCellForColumnAndRowParameterizedAttribute();
	public static final NSString NSAccessibilityCellForColumnAndRowParameterizedAttribute = new NSString(NSAccessibilityCellForColumnAndRowParameterizedAttribute());
	/** @method flags=const dynamic no_gen*/
	public static final native long /*int*/ NSAccessibilityCellRole();
	public static final NSString NSAccessibilityCellRole = new NSString(NSAccessibilityCellRole());
	
	/** 10.7 selectors and constants */
	public static final long /*int*/ sel_isCompatibleWithOverlayScrollers = sel_registerName("isCompatibleWithOverlayScrollers");
	public static final long /*int*/ sel_flashScrollers = sel_registerName("flashScrollers");
	public static final long /*int*/ sel_frameSizeForContentSize_horizontalScrollerClass_verticalScrollerClass_borderType_controlSize_scrollerStyle_ = sel_registerName("frameSizeForContentSize:horizontalScrollerClass:verticalScrollerClass:borderType:controlSize:scrollerStyle:");
	public static final long /*int*/ sel_scrollerStyle = sel_registerName("scrollerStyle");
	public static final long /*int*/ sel_toggleFullScreen_ = sel_registerName("toggleFullScreen:");

	public static final int NSScrollerStyleLegacy = 0;
	public static final int NSScrollerStyleOverlay = 1;
	public static final int NSWindowFullScreenButton = 7;
	public static final int NSFullScreenWindowMask = 1 << 14;
	public static final int NSWindowCollectionBehaviorFullScreenPrimary = 1 << 7;
	public static final int NSWindowCollectionBehaviorFullScreenAuxiliary = 1 << 8;

	/* AWT application delegate. Remove these when JavaRuntimeSupport.framework has bridgesupport generated for it. */
	public static final long /*int*/ class_JRSAppKitAWT = objc_getClass("JRSAppKitAWT");
	public static final long /*int*/ sel_awtAppDelegate = sel_registerName("awtAppDelegate");
	
	public static final long /*int*/ class_NSToolbarView = objc_getClass("NSToolbarView");

/** JNI natives */

/** @method flags=jni */
public static final native long /*int*/ NewGlobalRef(Object object);
/**
 * @method flags=jni
 * @param globalRef cast=(jobject)
 */
public static final native void DeleteGlobalRef(long /*int*/ globalRef);
/** @method flags=no_gen */ 
public static final native Object JNIGetObject(long /*int*/ globalRef);

/** Carbon calls */

public static final native int Gestalt(int selector, int[] response);
/** @param psn cast=(ProcessSerialNumber *) */
public static final native int GetCurrentProcess(int[] psn);
/** @param psn cast=(ProcessSerialNumber *) */
public static final native int SetFrontProcess(int[] psn);
/** @param psn cast=(ProcessSerialNumber *) */
public static final native int TransformProcessType(int[] psn, int transformState);
public static final native int CPSSetProcessName(int[] psn, long /*int*/ name);
/** @method flags=dynamic */
public static final native int SetThemeCursor(int themeCursor);
/** @method flags=dynamic */
public static final native int GetCurrentEventButtonState();
/** @method flags=dynamic */
public static final native int GetDblTime();
/** @method flags=dynamic
	@param inCreator cast=(OSType) 
	@param inType cast=(OSType) 
	@param inExtension cast=(CFStringRef) 
	@param inMIMEType cast=(CFStringRef) 
	@param inUsageFlags cast=(IconServicesUsageFlags) 
	@param outIconRef cast=(IconRef *) */
public static final native int GetIconRefFromTypeInfo(int inCreator, int inType, long /*int*/ inExtension, long /*int*/ inMIMEType, int inUsageFlags, long /*int*/ outIconRef[]);
/** @method flags=dynamic 
    @param context cast=(CGContextRef) */
public static final native long /*int*/ CGContextCopyPath(long /*int*/ context);
/** @method flags=dynamic */
public static final native long /*int*/ TISCopyCurrentKeyboardInputSource();
/** @method flags=dynamic 
    @param inputSource cast=(TISInputSourceRef) 
    @param propertyKey cast=(CFStringRef) */
public static final native long /*int*/ TISGetInputSourceProperty (long /*int*/ inputSource, long /*int*/ propertyKey);
/** @method flags=no_gen */
public static final native long /*int*/ kTISPropertyUnicodeKeyLayoutData();
/**
 * @method flags=dynamic
 * @param inMode cast=(UInt32)
 * @param inOptions cast=(UInt32)
 */
public static final native int SetSystemUIMode(int inMode, int inOptions);
/**
 * @method flags=dynamic
 * @param outMode cast=(UInt32*)
 * @param outOptions cast=(UInt32*)
 */
public static final native int GetSystemUIMode(int[] outMode, int[] outOptions);
/**
 * @method flags=dynamic
 * @param keyLayoutPtr cast=(const UCKeyboardLayout *)
 * @param virtualKeyCode cast=(UInt16)
 * @param keyAction cast=(UInt16)
 * @param modifierKeyState cast=(UInt32)
 * @param keyboardType cast=(UInt32)
 * @param keyTranslateOptions cast=(OptionBits)
 * @param deadKeyState cast=(UInt32 *)
 * @param maxStringLength cast=(UniCharCount)
 * @param actualStringLength cast=(UniCharCount *)
 * @param unicodeString cast=(UniChar *)
 */
public static final native int UCKeyTranslate (long /*int*/ keyLayoutPtr, short virtualKeyCode, short keyAction, int modifierKeyState, int keyboardType, int keyTranslateOptions, int[] deadKeyState, int maxStringLength, int[] actualStringLength, char[] unicodeString);
/**
 * @param inUTI1 cast=(CFStringRef)
 * @param inUTI2 cast=(CFStringRef)
 */
public static final native boolean UTTypeEqual(long /*int*/ inUTI1, long /*int*/ inUTI2);

/**
 * @method flags=dynamic
 * @param metric cast=(SInt32 *)
*/
public static final native void GetThemeMetric(int themeConstant, int[] metric);
/**
 * @method flags=dynamic
 * @param inContext cast=(CGContextRef)
*/
public static final native int HIThemeDrawFocusRect(CGRect inRect, boolean inHasFocus, long /*int*/ inContext, int inOrientation);

public static final int kUCKeyActionDown = 0;
public static final int kUCKeyActionUp = 1;

public static final int kThemeCopyArrowCursor = 1;
public static final int kThemeNotAllowedCursor = 18;
public static final int kThemeAliasArrowCursor = 2;

/** @method flags=dynamic 
 * @param iFile cast=(const FSRef *)
 * @param iContext cast=(ATSFontContext)
 * @param iFormat cast=(ATSFontFormat)
 * @param iReserved cast=(void *)
 * @param iOptions cast=(ATSOptionFlags)
 * @param oContainer cast=(ATSFontContainerRef *)
 */
public static final native int ATSFontActivateFromFileReference(byte[] iFile, int iContext, int iFormat, long /*int*/ iReserved, int iOptions, long /*int*/ [] oContainer);

public static final int kATSFontContextLocal = 2;
public static final int kATSOptionFlagsDefault = 0;
public static final int kATSFontFormatUnspecified = 0;

/** @method flags=dynamic 
 * @param path cast=(const UInt8 *)
 * @param ref cast=(FSRef *)
 * @param isDirectory cast=(Boolean *)
 */
public static final native int FSPathMakeRef (long /*int*/ path, byte[] ref, boolean[] isDirectory);

/** @method flags=dynamic */
public static final native byte LMGetKbdType();

/** @method flags=dynamic */
public static final native long /*int*/ AcquireRootMenu ();
/** @method flags=dynamic */
public static final native int CancelMenuTracking (long /*int*/ inRootMenu, boolean inImmediate, int inDismissalReason);
/**
 * @param inType cast=(OSType)
 * @param inCreator cast=(OSType)
 * @param inExtension cast=(CFStringRef)
 * @param inRoleMask cast=(LSRolesMask)
 * @param outAppRef cast=(FSRef *)
 * @param outAppURL cast=(CFURLRef *)
 */
public static final native long /*int*/ LSGetApplicationForInfo(int inType, int inCreator,long /*int*/ inExtension, int inRoleMask, byte[] outAppRef, int[] outAppURL);
/** @method flags=dynamic
 * @param mHandle cast=(MenuRef)
 * @param commandId cast=(MenuCommand)
 * @param index cast=(UInt32)
 * @param outMenu cast=(MenuRef *)
 * @param outIndex cast=(MenuItemIndex *)
 */
public static final native int GetIndMenuItemWithCommandID(long /*int*/ mHandle, int commandId, int index, long /*int*/ [] outMenu, short[] outIndex);
/** @method flags=dynamic
 * @param mHandle cast=(MenuRef)
 * @param index cast=(short)
 */
public static final native void DeleteMenuItem(long /*int*/ mHandle, short index);
/**
 * @param image_name cast=(const char*)
 * @param options cast=(uint32_t)
 */
public static final native long /*int*/ NSAddImage(byte[] image_name, int options);
public static final int NSADDIMAGE_OPTION_RETURN_ON_ERROR = 0x1;
public static final int NSADDIMAGE_OPTION_MATCH_FILENAME_BY_INSTALLNAME = 0x8;
/** @method flags=dynamic
 * @param pmSessionInfo cast=(PMPrintSession)
 * @param outPMPrinter cast=(PMPrinter *)
 */
public static final native long /*int*/ PMSessionGetCurrentPrinter(long /*int*/ pmSessionInfo, long /*int*/[] outPMPrinter);
/** @method flags=dynamic
 * @param pmSessionInfo cast=(PMPrintSession)
 * @param pmPrintSettings cast=(PMPrintSettings)
 */
public static final native long /*int*/ PMSessionGetDestinationType(long /*int*/ pmSessionInfo, long /*int*/ pmPrintSettings, short[] outDestinationType);
/** @method flags=dynamic
 * @param printSettings cast=(PMPrintSettings)
 * @param outDuplexSetting cast=(PMDuplexMode *)
 */
public static final native long /*int*/ PMGetDuplex(long /*int*/ printSettings, int[] outDuplexSetting);
/** @method flags=dynamic
 * @param printSettings cast=(PMPrintSettings)
 * @param duplexSetting cast=(PMDuplexMode)
 */
public static final native long /*int*/ PMSetDuplex(long /*int*/ printSettings, int duplexSetting);
/** @method flags=dynamic
 * @param pmPrinter cast=(PMPrinter)
 * @param outNumResolutions cast=(UInt32 *)
 */
public static final native long /*int*/ PMPrinterGetPrinterResolutionCount(long /*int*/ pmPrinter, int[] outNumResolutions);
/** @method flags=dynamic
 * @param pmPrinter cast=(PMPrinter)
 * @param pmPrintSettings cast=(PMPrintSettings)
 * @param outResolution cast=(PMResolution *)
 */
public static final native long /*int*/ PMPrinterGetOutputResolution(long /*int*/ pmPrinter, long /*int*/ pmPrintSettings, PMResolution outResolution);
/** @method flags=dynamic
 * @param pmPrinter cast=(PMPrinter)
 * @param outResolution cast=(PMResolution *)
 */
public static final native long /*int*/ PMPrinterGetIndexedPrinterResolution(long /*int*/ pmPrinter, int index, PMResolution outResolution);

// Custom FindWindow implementation to avoid namespace collisions with Point.
/**
 * @method flags=no_gen
 * @param wHandle cast=(WindowRef *)
 */
public static final native long /*int*/ FindWindow (long /*int*/ h, long /*int*/ v, long /*int*/ [] wHandle);

/**
 * @method flags=dynamic
 * @param display cast=(CGDirectDisplayID)
 */
public static final native long /*int*/ CGDisplayBaseAddress(int display);
/**
 * @method flags=dynamic
 * @param display cast=(CGDirectDisplayID)
 */
public static final native long /*int*/ CGDisplayBitsPerPixel(int display);
/**
 * @method flags=dynamic
 * @param display cast=(CGDirectDisplayID)
 */
public static final native long /*int*/ CGDisplayBitsPerSample(int display);
/**
 * @method flags=dynamic
 * @param display cast=(CGDirectDisplayID)
 */
public static final native long /*int*/ CGDisplayBytesPerRow(int display);


/** C calls */

public static final native int getpid();

public static final native void call(long /*int*/ proc, long /*int*/ id, long /*int*/ sel);

/** @method flags=no_gen */
public static final native boolean __BIG_ENDIAN__();
public static final int kCGBitmapByteOrderDefault = 0 << 12;
public static final int kCGBitmapByteOrder16Little = 1 << 12;
public static final int kCGBitmapByteOrder32Little = 2 << 12;
public static final int kCGBitmapByteOrder16Big = 3 << 12;
public static final int kCGBitmapByteOrder32Big = 4 << 12;
public static final int kCGBitmapByteOrder16Host = __BIG_ENDIAN__() ? kCGBitmapByteOrder16Big : kCGBitmapByteOrder16Little;
public static final int kCGBitmapByteOrder32Host = __BIG_ENDIAN__() ? kCGBitmapByteOrder32Big : kCGBitmapByteOrder32Little;

/**
 * @method flags=dynamic
 * @param destRect flags=struct
 * @param srcRect flags=struct
 */
public static final native void CGContextCopyWindowContentsToRect(long /*int*/ context, CGRect destRect, long /*int*/ contextID, long /*int*/ windowNumber, CGRect srcRect);

/**
 * @method flags=dynamic
 * @param displayID cast=(CGDirectDisplayID)
 */
public static final native long /*int*/ CGDisplayCreateImage(int displayID);

/** QuickDraw calls */

/** @method flags=dynamic */
public static final native long /*int*/ NewRgn();
/** @method flags=dynamic */
public static final native void RectRgn(long /*int*/ rgnHandle, short[] rect);
/** @method flags=dynamic */
public static final native void OpenRgn();
/** @method flags=dynamic */
public static final native void OffsetRgn(long /*int*/ rgnHandle, short dh, short dv);
/** @method flags=dynamic */
public static final native void MoveTo(short h, short v);
/** @method flags=dynamic */
public static final native void LineTo(short h, short v);
/** @method flags=dynamic */
public static final native void UnionRgn(long /*int*/ srcRgnA, long /*int*/ srcRgnB, long /*int*/ dstRgn);
/** @method flags=dynamic */
public static final native void CloseRgn(long /*int*/ dstRgn);
/** @method flags=dynamic */
public static final native void DisposeRgn(long /*int*/ rgnHandle);
/**
 * @method flags=dynamic
 * @param pt flags=struct,cast=(Point *)
 */
public static final native boolean PtInRgn(short[] pt, long /*int*/ rgnHandle);
/** @method flags=dynamic */
public static final native void GetRegionBounds(long /*int*/ rgnHandle, short[] bounds);
/** @method flags=dynamic */
public static final native void SectRgn(long /*int*/ srcRgnA, long /*int*/ srcRgnB, long /*int*/ dstRgn);
/** @method flags=dynamic */
public static final native boolean EmptyRgn(long /*int*/ rgnHandle);
/** @method flags=dynamic */
public static final native void DiffRgn(long /*int*/ srcRgnA, long /*int*/ srcRgnB, long /*int*/ dstRgn);
/** @method flags=dynamic */
public static final native boolean RectInRgn(short[] rect, long /*int*/ rgnHandle);
/** @method flags=dynamic */
public static final native int QDRegionToRects(long /*int*/ rgn, int dir, long /*int*/ proc, long /*int*/ userData);
/** @method flags=dynamic */
public static final native void CopyRgn(long /*int*/ srcRgnHandle, long /*int*/ dstRgnHandle);
/** @method flags=dynamic */
public static final native void SetRect(short[] r, short left, short top, short right, short bottom);
public static final int kQDParseRegionFromTop = (1 << 0);
public static final int kQDParseRegionFromBottom = (1 << 1);
public static final int kQDParseRegionFromLeft = (1 << 2);
public static final int kQDParseRegionFromRight = (1 << 3);
public static final int kQDParseRegionFromTopLeft = kQDParseRegionFromTop | kQDParseRegionFromLeft;
public static final int kQDRegionToRectsMsgParse = 2;

/**
 * @method flags=dynamic
 * @param inWindow cast=(WindowRef)
 */
public static final native int HIWindowGetCGWindowID(long /*int*/ inWindow);

/** JavaScriptCore calls */

/**
 * @param ctx cast=(JSContextRef)
 * @param script cast=(JSStringRef)
 * @param thisObject cast=(JSObjectRef)
 * @param sourceURL cast=(JSStringRef)
 * @param exception cast=(JSValueRef *)
 */
public static final native long /*int*/ JSEvaluateScript (long /*int*/ ctx, long /*int*/ script, long /*int*/ thisObject, long /*int*/ sourceURL, int startingLineNumber, long /*int*/[] exception);

/**
 * @param string cast=(const char *)
 */
public static final native long /*int*/ JSStringCreateWithUTF8CString (byte[] string);

/**
 * @param string cast=(JSStringRef)
 */
public static final native void JSStringRelease (long /*int*/ string);


/** Certificate Security */

/**
 * @param certType cast=(CSSM_CERT_TYPE)
 * @param policyOID cast=(CSSM_OID *)
 * @param value cast=(CSSM_DATA *)
 * @param policySearch cast=(SecPolicySearchRef *) 
 */
public static final native int SecPolicySearchCreate(long /*int*/ certType, long /*int*/ policyOID, long /*int*/ value, long /*int*/ [] policySearch);

/**
 * @param searchRef cast=(SecPolicySearchRef)
 * @param policyRef cast=(SecPolicyRef *)
 */
public static final native int SecPolicySearchCopyNext(long /*int*/ searchRef, long /*int*/ [] policyRef);

/** 
 * @param certificates cast=(CFArrayRef)
 * @param policies cast=(CFTypeRef)
 * @param trustRef cast=(SecTrustRef *) 
 */
public static final native int SecTrustCreateWithCertificates(long /*int*/ certificates, long /*int*/ policies, long /*int*/ [] trustRef);

public static final int CSSM_CERT_X_509v3 = 0x3;


/** Custom callbacks */

/** @method flags=no_gen */
public static final native long /*int*/ isFlipped_CALLBACK();

/** Custom structure return */

/** @method flags=no_gen */
public static final native void NSIntersectionRect (NSRect result, NSRect aRect, NSRect bRect);
/**
 * @method flags=no_gen
 * @param display cast=(CGDirectDisplayID)
 */
public static final native void CGDisplayBounds(int display, CGRect rect);

/** @method flags=const address*/
public static final native long /*int*/ kCFTypeDictionaryKeyCallBacks();
/** @method flags=const address*/
public static final native long /*int*/ kCFTypeDictionaryValueCallBacks();

/** @method flags=const */
public static final native long /*int*/ kUTTypeFileURL();
public static final NSString kUTTypeFileURL = new NSString(kUTTypeFileURL());
/** @method flags=const */
public static final native long /*int*/ kUTTypeURL();
public static final NSString kUTTypeURL = new NSString(kUTTypeURL());

/** Objective-C runtime */

/**
 * @param cls cast=(Class)
 * @param name cast=(const char *),flags=critical
 * @param types cast=(const char *),flags=critical
 */
public static final native boolean class_addIvar(long /*int*/ cls, byte[] name, long /*int*/ size, byte alignment, byte[] types);
/**
 * @param cls cast=(Class)
 * @param name cast=(SEL)
 * @param imp cast=(IMP)
 */
public static final native boolean class_addMethod(long /*int*/ cls, long /*int*/ name, long /*int*/ imp, String types);
/**
 * @param cls cast=(Class)
 * @param protocol cast=(Protocol *)
 */
public static final native boolean class_addProtocol(long /*int*/ cls, long /*int*/ protocol);
/**
 * @param aClass cast=(Class)
 * @param aSelector cast=(SEL)
 */
public static final native long /*int*/ class_getClassMethod(long /*int*/ aClass, long /*int*/ aSelector);
/**
 * @param cls cast=(Class)
 * @param name cast=(SEL)
 */
public static final native long /*int*/ class_getMethodImplementation(long /*int*/ cls, long /*int*/ name);
/**
 * @param cls cast=(Class)
 * @param name cast=(SEL)
 */
public static final native long /*int*/ class_getInstanceMethod(long /*int*/ cls, long /*int*/ name);
/** @param cls cast=(Class) */
public static final native long /*int*/ class_getSuperclass(long /*int*/ cls);
/**
 * @param method cast=(Method)
 * @param imp cast=(IMP)
 */
public static final native long /*int*/ method_setImplementation(long /*int*/ method, long /*int*/ imp);
/**
 * @param sel cast=(SEL)
 */
public static final native long /*int*/ sel_getName(long /*int*/ sel);
/**
 * @param cls cast=(Class)
 * @param extraBytes cast=(size_t)
 */
public static final native long /*int*/ class_createInstance(long /*int*/ cls, long /*int*/ extraBytes);

/** @method flags=no_gen */
public static final native String class_getName(long /*int*/ cls);
/** @method flags=dynamic */
public static final native void instrumentObjcMessageSends(boolean val);
/** @param superclass cast=(Class) */
public static final native long /*int*/ objc_allocateClassPair(long /*int*/ superclass, String name, long /*int*/ extraBytes);
/** @param klass cast=(Class) */
public static final native void objc_disposeClassPair(long /*int*/ klass);
public static final native long /*int*/ objc_getClass(String className);
public static final native long /*int*/ objc_getMetaClass(String name);
public static final native long /*int*/ objc_getProtocol(String name);
public static final native long /*int*/ objc_lookUpClass(String className);
/** @param cls cast=(Class) */
public static final native void objc_registerClassPair(long /*int*/ cls);
/** @param obj cast=(id) */
public static final native long /*int*/ object_getClassName(long /*int*/ obj);
/** @param obj cast=(id) */
public static final native long /*int*/ object_getClass(long /*int*/ obj);

/**
 * @param obj cast=(id)
 * @param name cast=(const char*),flags=critical
 * @param outValue cast=(void **),flags=critical
 */
public static final native long /*int*/ object_getInstanceVariable(long /*int*/ obj, byte[] name, long /*int*/ [] outValue);
/**
 * @param obj cast=(id)
 * @param name cast=(const char*),flags=critical
 * @param value cast=(void *),flags=critical
 */
public static final native long /*int*/ object_setInstanceVariable(long /*int*/ obj, byte[] name, long /*int*/ value);
/**
 * @param obj cast=(id)
 * @param clazz cast=(Class) 
 */
public static final native long /*int*/ object_setClass(long /*int*/ obj, long /*int*/ clazz);
public static final native long /*int*/ sel_registerName(String selectorName);
public static final native int objc_super_sizeof();

/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSSize result, long /*int*/ id, long /*int*/ sel, NSSize arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3, long /*int*/ arg4, long /*int*/ arg5);


/** This section is auto generated */

/** Custom callbacks */
/** @method callback_types=id;id;SEL;NSPoint;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_accessibilityHitTest_(long /*int*/ func);
/** @method callback_types=NSAttributedString*;id;SEL;NSRange;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_attributedSubstringFromRange_(long /*int*/ func);
/** @method callback_types=BOOL;id;SEL;NSIndexSet*;NSPoint;,callback_flags=none;none;none;none;struct; */
public static final native long /*int*/ CALLBACK_canDragRowsWithIndexes_atPoint_(long /*int*/ func);
/** @method callback_types=NSSize;id;SEL;,callback_flags=struct;none;none; */
public static final native long /*int*/ CALLBACK_cellSize(long /*int*/ func);
/** @method callback_types=NSSize;id;SEL;NSRect;,callback_flags=struct;none;none;struct; */
public static final native long /*int*/ CALLBACK_cellSizeForBounds_(long /*int*/ func);
/** @method callback_types=NSUInteger;id;SEL;NSPoint;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_characterIndexForPoint_(long /*int*/ func);
/** @method callback_types=NSInteger;id;SEL;NSPoint;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_columnAtPoint_(long /*int*/ func);
/** @method callback_types=BOOL;id;SEL;NSEvent*;NSSize;BOOL;,callback_flags=none;none;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_dragSelectionWithEvent_offset_slideBack_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSImage*;NSPoint;,callback_flags=none;none;none;none;struct; */
public static final native long /*int*/ CALLBACK_draggedImage_beganAt_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSImage*;NSPoint;NSDragOperation;,callback_flags=none;none;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_draggedImage_endedAt_operation_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSRect;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_drawBackgroundInClipRect_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSImage*;NSRect;NSView*;,callback_flags=none;none;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_drawImage_withFrame_inView_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSRect;NSView*;,callback_flags=none;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_drawInteriorWithFrame_inView_(long /*int*/ func);
/** @method callback_types=void;id;SEL;BOOL;NSRect;,callback_flags=none;none;none;none;struct; */
public static final native long /*int*/ CALLBACK_drawLabel_inRect_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSRect;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_drawRect_(long /*int*/ func);
/** @method callback_types=NSRect;id;SEL;NSAttributedString*;NSRect;NSView*;,callback_flags=struct;none;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_drawTitle_withFrame_inView_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSRect;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_drawViewBackgroundInRect_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSRect;NSView*;,callback_flags=none;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_drawWithExpansionFrame_inView_(long /*int*/ func);
/** @method callback_types=NSRect;id;SEL;NSRect;NSView*;,callback_flags=struct;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_expansionFrameWithFrame_inView_(long /*int*/ func);
/** @method callback_types=NSRect;id;SEL;NSRange;,callback_flags=struct;none;none;struct; */
public static final native long /*int*/ CALLBACK_firstRectForCharacterRange_(long /*int*/ func);
/** @method callback_types=NSRect;id;SEL;NSInteger;,callback_flags=struct;none;none;none; */
public static final native long /*int*/ CALLBACK_headerRectOfColumn_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSRect;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_highlightSelectionInClipRect_(long /*int*/ func);
/** @method callback_types=NSView*;id;SEL;NSPoint;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_hitTest_(long /*int*/ func);
/** @method callback_types=NSUInteger;id;SEL;NSEvent*;NSRect;NSView*;,callback_flags=none;none;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_hitTestForEvent_inRect_ofView_(long /*int*/ func);
/** @method callback_types=NSRect;id;SEL;NSRect;,callback_flags=struct;none;none;struct; */
public static final native long /*int*/ CALLBACK_imageRectForBounds_(long /*int*/ func);
/** @method callback_types=NSRange;id;SEL;,callback_flags=struct;none;none; */
public static final native long /*int*/ CALLBACK_markedRange(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSClipView*;NSPoint;,callback_flags=none;none;none;none;struct; */
public static final native long /*int*/ CALLBACK_scrollClipView_toPoint_(long /*int*/ func);
/** @method callback_types=NSRange;id;SEL;,callback_flags=struct;none;none; */
public static final native long /*int*/ CALLBACK_selectedRange(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSRect;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_setFrame_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSPoint;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_setFrameOrigin_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSSize;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_setFrameSize_(long /*int*/ func);
/** @method callback_types=void;id;SEL;id;NSRange;,callback_flags=none;none;none;none;struct; */
public static final native long /*int*/ CALLBACK_setMarkedText_selectedRange_(long /*int*/ func);
/** @method callback_types=void;id;SEL;NSRect;,callback_flags=none;none;none;struct; */
public static final native long /*int*/ CALLBACK_setNeedsDisplayInRect_(long /*int*/ func);
/** @method callback_types=BOOL;id;SEL;NSRange;NSString*;,callback_flags=none;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_shouldChangeTextInRange_replacementString_(long /*int*/ func);
/** @method callback_types=NSSize;id;SEL;BOOL;,callback_flags=struct;none;none;none; */
public static final native long /*int*/ CALLBACK_sizeOfLabel_(long /*int*/ func);
/** @method callback_types=NSRange;id;SEL;NSTextView*;NSRange;NSRange;,callback_flags=struct;none;none;none;struct;struct; */
public static final native long /*int*/ CALLBACK_textView_willChangeSelectionFromCharacterRange_toCharacterRange_(long /*int*/ func);
/** @method callback_types=NSRect;id;SEL;NSRect;,callback_flags=struct;none;none;struct; */
public static final native long /*int*/ CALLBACK_titleRectForBounds_(long /*int*/ func);
/** @method callback_types=NSString*;id;SEL;NSView*;NSToolTipTag;NSPoint;void*;,callback_flags=none;none;none;none;none;struct;none; */
public static final native long /*int*/ CALLBACK_view_stringForToolTip_point_userData_(long /*int*/ func);
/** @method callback_types=void;id;SEL;WebView*;NSRect;,callback_flags=none;none;none;none;struct; */
public static final native long /*int*/ CALLBACK_webView_setFrame_(long /*int*/ func);

/** Classes */
public static final long /*int*/ class_DOMDocument = objc_getClass("DOMDocument");
public static final long /*int*/ class_DOMEvent = objc_getClass("DOMEvent");
public static final long /*int*/ class_DOMKeyboardEvent = objc_getClass("DOMKeyboardEvent");
public static final long /*int*/ class_DOMMouseEvent = objc_getClass("DOMMouseEvent");
public static final long /*int*/ class_DOMUIEvent = objc_getClass("DOMUIEvent");
public static final long /*int*/ class_DOMWheelEvent = objc_getClass("DOMWheelEvent");
public static final long /*int*/ class_NSActionCell = objc_getClass("NSActionCell");
public static final long /*int*/ class_NSAffineTransform = objc_getClass("NSAffineTransform");
public static final long /*int*/ class_NSAlert = objc_getClass("NSAlert");
public static final long /*int*/ class_NSAppleEventDescriptor = objc_getClass("NSAppleEventDescriptor");
public static final long /*int*/ class_NSApplication = objc_getClass("NSApplication");
public static final long /*int*/ class_NSArray = objc_getClass("NSArray");
public static final long /*int*/ class_NSAssertionHandler = objc_getClass("NSAssertionHandler");
public static final long /*int*/ class_NSAttributedString = objc_getClass("NSAttributedString");
public static final long /*int*/ class_NSAutoreleasePool = objc_getClass("NSAutoreleasePool");
public static final long /*int*/ class_NSBezierPath = objc_getClass("NSBezierPath");
public static final long /*int*/ class_NSBitmapImageRep = objc_getClass("NSBitmapImageRep");
public static final long /*int*/ class_NSBox = objc_getClass("NSBox");
public static final long /*int*/ class_NSBrowserCell = objc_getClass("NSBrowserCell");
public static final long /*int*/ class_NSBundle = objc_getClass("NSBundle");
public static final long /*int*/ class_NSButton = objc_getClass("NSButton");
public static final long /*int*/ class_NSButtonCell = objc_getClass("NSButtonCell");
public static final long /*int*/ class_NSCalendarDate = objc_getClass("NSCalendarDate");
public static final long /*int*/ class_NSCell = objc_getClass("NSCell");
public static final long /*int*/ class_NSCharacterSet = objc_getClass("NSCharacterSet");
public static final long /*int*/ class_NSClipView = objc_getClass("NSClipView");
public static final long /*int*/ class_NSCoder = objc_getClass("NSCoder");
public static final long /*int*/ class_NSColor = objc_getClass("NSColor");
public static final long /*int*/ class_NSColorList = objc_getClass("NSColorList");
public static final long /*int*/ class_NSColorPanel = objc_getClass("NSColorPanel");
public static final long /*int*/ class_NSColorSpace = objc_getClass("NSColorSpace");
public static final long /*int*/ class_NSComboBox = objc_getClass("NSComboBox");
public static final long /*int*/ class_NSComboBoxCell = objc_getClass("NSComboBoxCell");
public static final long /*int*/ class_NSControl = objc_getClass("NSControl");
public static final long /*int*/ class_NSCursor = objc_getClass("NSCursor");
public static final long /*int*/ class_NSData = objc_getClass("NSData");
public static final long /*int*/ class_NSDate = objc_getClass("NSDate");
public static final long /*int*/ class_NSDatePicker = objc_getClass("NSDatePicker");
public static final long /*int*/ class_NSDictionary = objc_getClass("NSDictionary");
public static final long /*int*/ class_NSDirectoryEnumerator = objc_getClass("NSDirectoryEnumerator");
public static final long /*int*/ class_NSDockTile = objc_getClass("NSDockTile");
public static final long /*int*/ class_NSEnumerator = objc_getClass("NSEnumerator");
public static final long /*int*/ class_NSError = objc_getClass("NSError");
public static final long /*int*/ class_NSEvent = objc_getClass("NSEvent");
public static final long /*int*/ class_NSFileManager = objc_getClass("NSFileManager");
public static final long /*int*/ class_NSFileWrapper = objc_getClass("NSFileWrapper");
public static final long /*int*/ class_NSFont = objc_getClass("NSFont");
public static final long /*int*/ class_NSFontManager = objc_getClass("NSFontManager");
public static final long /*int*/ class_NSFontPanel = objc_getClass("NSFontPanel");
public static final long /*int*/ class_NSFormatter = objc_getClass("NSFormatter");
public static final long /*int*/ class_NSGradient = objc_getClass("NSGradient");
public static final long /*int*/ class_NSGraphicsContext = objc_getClass("NSGraphicsContext");
public static final long /*int*/ class_NSHTTPCookie = objc_getClass("NSHTTPCookie");
public static final long /*int*/ class_NSHTTPCookieStorage = objc_getClass("NSHTTPCookieStorage");
public static final long /*int*/ class_NSImage = objc_getClass("NSImage");
public static final long /*int*/ class_NSImageRep = objc_getClass("NSImageRep");
public static final long /*int*/ class_NSImageView = objc_getClass("NSImageView");
public static final long /*int*/ class_NSIndexSet = objc_getClass("NSIndexSet");
public static final long /*int*/ class_NSInputManager = objc_getClass("NSInputManager");
public static final long /*int*/ class_NSKeyedArchiver = objc_getClass("NSKeyedArchiver");
public static final long /*int*/ class_NSKeyedUnarchiver = objc_getClass("NSKeyedUnarchiver");
public static final long /*int*/ class_NSLayoutManager = objc_getClass("NSLayoutManager");
public static final long /*int*/ class_NSLocale = objc_getClass("NSLocale");
public static final long /*int*/ class_NSMenu = objc_getClass("NSMenu");
public static final long /*int*/ class_NSMenuItem = objc_getClass("NSMenuItem");
public static final long /*int*/ class_NSMutableArray = objc_getClass("NSMutableArray");
public static final long /*int*/ class_NSMutableAttributedString = objc_getClass("NSMutableAttributedString");
public static final long /*int*/ class_NSMutableDictionary = objc_getClass("NSMutableDictionary");
public static final long /*int*/ class_NSMutableIndexSet = objc_getClass("NSMutableIndexSet");
public static final long /*int*/ class_NSMutableParagraphStyle = objc_getClass("NSMutableParagraphStyle");
public static final long /*int*/ class_NSMutableSet = objc_getClass("NSMutableSet");
public static final long /*int*/ class_NSMutableString = objc_getClass("NSMutableString");
public static final long /*int*/ class_NSMutableURLRequest = objc_getClass("NSMutableURLRequest");
public static final long /*int*/ class_NSNotification = objc_getClass("NSNotification");
public static final long /*int*/ class_NSNotificationCenter = objc_getClass("NSNotificationCenter");
public static final long /*int*/ class_NSNumber = objc_getClass("NSNumber");
public static final long /*int*/ class_NSNumberFormatter = objc_getClass("NSNumberFormatter");
public static final long /*int*/ class_NSObject = objc_getClass("NSObject");
public static final long /*int*/ class_NSOpenGLContext = objc_getClass("NSOpenGLContext");
public static final long /*int*/ class_NSOpenGLPixelFormat = objc_getClass("NSOpenGLPixelFormat");
public static final long /*int*/ class_NSOpenPanel = objc_getClass("NSOpenPanel");
public static final long /*int*/ class_NSOutlineView = objc_getClass("NSOutlineView");
public static final long /*int*/ class_NSPanel = objc_getClass("NSPanel");
public static final long /*int*/ class_NSParagraphStyle = objc_getClass("NSParagraphStyle");
public static final long /*int*/ class_NSPasteboard = objc_getClass("NSPasteboard");
public static final long /*int*/ class_NSPopUpButton = objc_getClass("NSPopUpButton");
public static final long /*int*/ class_NSPrintInfo = objc_getClass("NSPrintInfo");
public static final long /*int*/ class_NSPrintOperation = objc_getClass("NSPrintOperation");
public static final long /*int*/ class_NSPrintPanel = objc_getClass("NSPrintPanel");
public static final long /*int*/ class_NSPrinter = objc_getClass("NSPrinter");
public static final long /*int*/ class_NSProgressIndicator = objc_getClass("NSProgressIndicator");
public static final long /*int*/ class_NSResponder = objc_getClass("NSResponder");
public static final long /*int*/ class_NSRunLoop = objc_getClass("NSRunLoop");
public static final long /*int*/ class_NSSavePanel = objc_getClass("NSSavePanel");
public static final long /*int*/ class_NSScreen = objc_getClass("NSScreen");
public static final long /*int*/ class_NSScrollView = objc_getClass("NSScrollView");
public static final long /*int*/ class_NSScroller = objc_getClass("NSScroller");
public static final long /*int*/ class_NSSearchField = objc_getClass("NSSearchField");
public static final long /*int*/ class_NSSearchFieldCell = objc_getClass("NSSearchFieldCell");
public static final long /*int*/ class_NSSecureTextField = objc_getClass("NSSecureTextField");
public static final long /*int*/ class_NSSegmentedCell = objc_getClass("NSSegmentedCell");
public static final long /*int*/ class_NSSet = objc_getClass("NSSet");
public static final long /*int*/ class_NSSlider = objc_getClass("NSSlider");
public static final long /*int*/ class_NSStatusBar = objc_getClass("NSStatusBar");
public static final long /*int*/ class_NSStatusItem = objc_getClass("NSStatusItem");
public static final long /*int*/ class_NSStepper = objc_getClass("NSStepper");
public static final long /*int*/ class_NSString = objc_getClass("NSString");
public static final long /*int*/ class_NSTabView = objc_getClass("NSTabView");
public static final long /*int*/ class_NSTabViewItem = objc_getClass("NSTabViewItem");
public static final long /*int*/ class_NSTableColumn = objc_getClass("NSTableColumn");
public static final long /*int*/ class_NSTableHeaderCell = objc_getClass("NSTableHeaderCell");
public static final long /*int*/ class_NSTableHeaderView = objc_getClass("NSTableHeaderView");
public static final long /*int*/ class_NSTableView = objc_getClass("NSTableView");
public static final long /*int*/ class_NSText = objc_getClass("NSText");
public static final long /*int*/ class_NSTextAttachment = objc_getClass("NSTextAttachment");
public static final long /*int*/ class_NSTextContainer = objc_getClass("NSTextContainer");
public static final long /*int*/ class_NSTextField = objc_getClass("NSTextField");
public static final long /*int*/ class_NSTextFieldCell = objc_getClass("NSTextFieldCell");
public static final long /*int*/ class_NSTextStorage = objc_getClass("NSTextStorage");
public static final long /*int*/ class_NSTextTab = objc_getClass("NSTextTab");
public static final long /*int*/ class_NSTextView = objc_getClass("NSTextView");
public static final long /*int*/ class_NSThread = objc_getClass("NSThread");
public static final long /*int*/ class_NSTimeZone = objc_getClass("NSTimeZone");
public static final long /*int*/ class_NSTimer = objc_getClass("NSTimer");
public static final long /*int*/ class_NSToolbar = objc_getClass("NSToolbar");
public static final long /*int*/ class_NSToolbarItem = objc_getClass("NSToolbarItem");
public static final long /*int*/ class_NSTouch = objc_getClass("NSTouch");
public static final long /*int*/ class_NSTrackingArea = objc_getClass("NSTrackingArea");
public static final long /*int*/ class_NSTypesetter = objc_getClass("NSTypesetter");
public static final long /*int*/ class_NSURL = objc_getClass("NSURL");
public static final long /*int*/ class_NSURLAuthenticationChallenge = objc_getClass("NSURLAuthenticationChallenge");
public static final long /*int*/ class_NSURLCredential = objc_getClass("NSURLCredential");
public static final long /*int*/ class_NSURLDownload = objc_getClass("NSURLDownload");
public static final long /*int*/ class_NSURLProtectionSpace = objc_getClass("NSURLProtectionSpace");
public static final long /*int*/ class_NSURLRequest = objc_getClass("NSURLRequest");
public static final long /*int*/ class_NSUndoManager = objc_getClass("NSUndoManager");
public static final long /*int*/ class_NSUserDefaults = objc_getClass("NSUserDefaults");
public static final long /*int*/ class_NSValue = objc_getClass("NSValue");
public static final long /*int*/ class_NSView = objc_getClass("NSView");
public static final long /*int*/ class_NSWindow = objc_getClass("NSWindow");
public static final long /*int*/ class_NSWorkspace = objc_getClass("NSWorkspace");
public static final long /*int*/ class_SFCertificatePanel = objc_getClass("SFCertificatePanel");
public static final long /*int*/ class_SFCertificateTrustPanel = objc_getClass("SFCertificateTrustPanel");
public static final long /*int*/ class_WebDataSource = objc_getClass("WebDataSource");
public static final long /*int*/ class_WebFrame = objc_getClass("WebFrame");
public static final long /*int*/ class_WebFrameView = objc_getClass("WebFrameView");
public static final long /*int*/ class_WebPreferences = objc_getClass("WebPreferences");
public static final long /*int*/ class_WebScriptObject = objc_getClass("WebScriptObject");
public static final long /*int*/ class_WebUndefined = objc_getClass("WebUndefined");
public static final long /*int*/ class_WebView = objc_getClass("WebView");

/** Protocols */
public static final long /*int*/ protocol_NSAccessibility = objc_getProtocol("NSAccessibility");
public static final long /*int*/ protocol_NSAccessibilityAdditions = objc_getProtocol("NSAccessibilityAdditions");
public static final long /*int*/ protocol_NSApplicationDelegate = objc_getProtocol("NSApplicationDelegate");
public static final long /*int*/ protocol_NSApplicationNotifications = objc_getProtocol("NSApplicationNotifications");
public static final long /*int*/ protocol_NSColorPanelResponderMethod = objc_getProtocol("NSColorPanelResponderMethod");
public static final long /*int*/ protocol_NSComboBoxNotifications = objc_getProtocol("NSComboBoxNotifications");
public static final long /*int*/ protocol_NSDraggingDestination = objc_getProtocol("NSDraggingDestination");
public static final long /*int*/ protocol_NSDraggingSource = objc_getProtocol("NSDraggingSource");
public static final long /*int*/ protocol_NSFontManagerResponderMethod = objc_getProtocol("NSFontManagerResponderMethod");
public static final long /*int*/ protocol_NSFontPanelValidationAdditions = objc_getProtocol("NSFontPanelValidationAdditions");
public static final long /*int*/ protocol_NSMenuDelegate = objc_getProtocol("NSMenuDelegate");
public static final long /*int*/ protocol_NSMenuValidation = objc_getProtocol("NSMenuValidation");
public static final long /*int*/ protocol_NSOutlineViewDataSource = objc_getProtocol("NSOutlineViewDataSource");
public static final long /*int*/ protocol_NSOutlineViewDelegate = objc_getProtocol("NSOutlineViewDelegate");
public static final long /*int*/ protocol_NSOutlineViewNotifications = objc_getProtocol("NSOutlineViewNotifications");
public static final long /*int*/ protocol_NSPasteboardOwner = objc_getProtocol("NSPasteboardOwner");
public static final long /*int*/ protocol_NSSavePanelDelegate = objc_getProtocol("NSSavePanelDelegate");
public static final long /*int*/ protocol_NSTabViewDelegate = objc_getProtocol("NSTabViewDelegate");
public static final long /*int*/ protocol_NSTableDataSource = objc_getProtocol("NSTableDataSource");
public static final long /*int*/ protocol_NSTableViewDelegate = objc_getProtocol("NSTableViewDelegate");
public static final long /*int*/ protocol_NSTableViewNotifications = objc_getProtocol("NSTableViewNotifications");
public static final long /*int*/ protocol_NSTextDelegate = objc_getProtocol("NSTextDelegate");
public static final long /*int*/ protocol_NSTextInput = objc_getProtocol("NSTextInput");
public static final long /*int*/ protocol_NSTextViewDelegate = objc_getProtocol("NSTextViewDelegate");
public static final long /*int*/ protocol_NSToolTipOwner = objc_getProtocol("NSToolTipOwner");
public static final long /*int*/ protocol_NSToolbarDelegate = objc_getProtocol("NSToolbarDelegate");
public static final long /*int*/ protocol_NSToolbarNotifications = objc_getProtocol("NSToolbarNotifications");
public static final long /*int*/ protocol_NSURLDownloadDelegate = objc_getProtocol("NSURLDownloadDelegate");
public static final long /*int*/ protocol_NSWindowDelegate = objc_getProtocol("NSWindowDelegate");
public static final long /*int*/ protocol_NSWindowNotifications = objc_getProtocol("NSWindowNotifications");
public static final long /*int*/ protocol_WebDocumentRepresentation = objc_getProtocol("WebDocumentRepresentation");
public static final long /*int*/ protocol_WebFrameLoadDelegate = objc_getProtocol("WebFrameLoadDelegate");
public static final long /*int*/ protocol_WebOpenPanelResultListener = objc_getProtocol("WebOpenPanelResultListener");
public static final long /*int*/ protocol_WebPolicyDecisionListener = objc_getProtocol("WebPolicyDecisionListener");
public static final long /*int*/ protocol_WebPolicyDelegate = objc_getProtocol("WebPolicyDelegate");
public static final long /*int*/ protocol_WebResourceLoadDelegate = objc_getProtocol("WebResourceLoadDelegate");
public static final long /*int*/ protocol_WebUIDelegate = objc_getProtocol("WebUIDelegate");

/** Selectors */
public static final long /*int*/ sel_CGEvent = sel_registerName("CGEvent");
public static final long /*int*/ sel_DOMDocument = sel_registerName("DOMDocument");
public static final long /*int*/ sel_IBeamCursor = sel_registerName("IBeamCursor");
public static final long /*int*/ sel_PMPrintSession = sel_registerName("PMPrintSession");
public static final long /*int*/ sel_PMPrintSettings = sel_registerName("PMPrintSettings");
public static final long /*int*/ sel_TIFFRepresentation = sel_registerName("TIFFRepresentation");
public static final long /*int*/ sel_URL = sel_registerName("URL");
public static final long /*int*/ sel_URLFromPasteboard_ = sel_registerName("URLFromPasteboard:");
public static final long /*int*/ sel_URLWithString_ = sel_registerName("URLWithString:");
public static final long /*int*/ sel_URLsForDirectory_inDomains_ = sel_registerName("URLsForDirectory:inDomains:");
public static final long /*int*/ sel_UTF8String = sel_registerName("UTF8String");
public static final long /*int*/ sel_abortEditing = sel_registerName("abortEditing");
public static final long /*int*/ sel_absoluteString = sel_registerName("absoluteString");
public static final long /*int*/ sel_acceptsFirstMouse_ = sel_registerName("acceptsFirstMouse:");
public static final long /*int*/ sel_acceptsFirstResponder = sel_registerName("acceptsFirstResponder");
public static final long /*int*/ sel_accessibilityActionDescription_ = sel_registerName("accessibilityActionDescription:");
public static final long /*int*/ sel_accessibilityActionNames = sel_registerName("accessibilityActionNames");
public static final long /*int*/ sel_accessibilityAttributeNames = sel_registerName("accessibilityAttributeNames");
public static final long /*int*/ sel_accessibilityAttributeValue_ = sel_registerName("accessibilityAttributeValue:");
public static final long /*int*/ sel_accessibilityAttributeValue_forParameter_ = sel_registerName("accessibilityAttributeValue:forParameter:");
public static final long /*int*/ sel_accessibilityFocusedUIElement = sel_registerName("accessibilityFocusedUIElement");
public static final long /*int*/ sel_accessibilityHitTest_ = sel_registerName("accessibilityHitTest:");
public static final long /*int*/ sel_accessibilityIsAttributeSettable_ = sel_registerName("accessibilityIsAttributeSettable:");
public static final long /*int*/ sel_accessibilityIsIgnored = sel_registerName("accessibilityIsIgnored");
public static final long /*int*/ sel_accessibilityParameterizedAttributeNames = sel_registerName("accessibilityParameterizedAttributeNames");
public static final long /*int*/ sel_accessibilityPerformAction_ = sel_registerName("accessibilityPerformAction:");
public static final long /*int*/ sel_accessibilitySetOverrideValue_forAttribute_ = sel_registerName("accessibilitySetOverrideValue:forAttribute:");
public static final long /*int*/ sel_accessibilitySetValue_forAttribute_ = sel_registerName("accessibilitySetValue:forAttribute:");
public static final long /*int*/ sel_action = sel_registerName("action");
public static final long /*int*/ sel_activateIgnoringOtherApps_ = sel_registerName("activateIgnoringOtherApps:");
public static final long /*int*/ sel_addAttribute_value_range_ = sel_registerName("addAttribute:value:range:");
public static final long /*int*/ sel_addButtonWithTitle_ = sel_registerName("addButtonWithTitle:");
public static final long /*int*/ sel_addChildWindow_ordered_ = sel_registerName("addChildWindow:ordered:");
public static final long /*int*/ sel_addClip = sel_registerName("addClip");
public static final long /*int*/ sel_addEventListener_listener_useCapture_ = sel_registerName("addEventListener:listener:useCapture:");
public static final long /*int*/ sel_addIndex_ = sel_registerName("addIndex:");
public static final long /*int*/ sel_addItem_ = sel_registerName("addItem:");
public static final long /*int*/ sel_addItemWithObjectValue_ = sel_registerName("addItemWithObjectValue:");
public static final long /*int*/ sel_addItemWithTitle_action_keyEquivalent_ = sel_registerName("addItemWithTitle:action:keyEquivalent:");
public static final long /*int*/ sel_addLayoutManager_ = sel_registerName("addLayoutManager:");
public static final long /*int*/ sel_addObject_ = sel_registerName("addObject:");
public static final long /*int*/ sel_addObjectsFromArray_ = sel_registerName("addObjectsFromArray:");
public static final long /*int*/ sel_addObserver_selector_name_object_ = sel_registerName("addObserver:selector:name:object:");
public static final long /*int*/ sel_addRepresentation_ = sel_registerName("addRepresentation:");
public static final long /*int*/ sel_addSubview_ = sel_registerName("addSubview:");
public static final long /*int*/ sel_addSubview_positioned_relativeTo_ = sel_registerName("addSubview:positioned:relativeTo:");
public static final long /*int*/ sel_addTabStop_ = sel_registerName("addTabStop:");
public static final long /*int*/ sel_addTabViewItem_ = sel_registerName("addTabViewItem:");
public static final long /*int*/ sel_addTableColumn_ = sel_registerName("addTableColumn:");
public static final long /*int*/ sel_addTemporaryAttribute_value_forCharacterRange_ = sel_registerName("addTemporaryAttribute:value:forCharacterRange:");
public static final long /*int*/ sel_addTextContainer_ = sel_registerName("addTextContainer:");
public static final long /*int*/ sel_addTimer_forMode_ = sel_registerName("addTimer:forMode:");
public static final long /*int*/ sel_addToolTipRect_owner_userData_ = sel_registerName("addToolTipRect:owner:userData:");
public static final long /*int*/ sel_addTypes_owner_ = sel_registerName("addTypes:owner:");
public static final long /*int*/ sel_alignment = sel_registerName("alignment");
public static final long /*int*/ sel_allKeys = sel_registerName("allKeys");
public static final long /*int*/ sel_allObjects = sel_registerName("allObjects");
public static final long /*int*/ sel_alloc = sel_registerName("alloc");
public static final long /*int*/ sel_allowsColumnReordering = sel_registerName("allowsColumnReordering");
public static final long /*int*/ sel_allowsFloats = sel_registerName("allowsFloats");
public static final long /*int*/ sel_alphaComponent = sel_registerName("alphaComponent");
public static final long /*int*/ sel_alphaValue = sel_registerName("alphaValue");
public static final long /*int*/ sel_altKey = sel_registerName("altKey");
public static final long /*int*/ sel_alternateSelectedControlColor = sel_registerName("alternateSelectedControlColor");
public static final long /*int*/ sel_alternateSelectedControlTextColor = sel_registerName("alternateSelectedControlTextColor");
public static final long /*int*/ sel_alwaysShowsDecimalSeparator = sel_registerName("alwaysShowsDecimalSeparator");
public static final long /*int*/ sel_appendAttributedString_ = sel_registerName("appendAttributedString:");
public static final long /*int*/ sel_appendBezierPath_ = sel_registerName("appendBezierPath:");
public static final long /*int*/ sel_appendBezierPathWithArcWithCenter_radius_startAngle_endAngle_ = sel_registerName("appendBezierPathWithArcWithCenter:radius:startAngle:endAngle:");
public static final long /*int*/ sel_appendBezierPathWithArcWithCenter_radius_startAngle_endAngle_clockwise_ = sel_registerName("appendBezierPathWithArcWithCenter:radius:startAngle:endAngle:clockwise:");
public static final long /*int*/ sel_appendBezierPathWithGlyphs_count_inFont_ = sel_registerName("appendBezierPathWithGlyphs:count:inFont:");
public static final long /*int*/ sel_appendBezierPathWithOvalInRect_ = sel_registerName("appendBezierPathWithOvalInRect:");
public static final long /*int*/ sel_appendBezierPathWithRect_ = sel_registerName("appendBezierPathWithRect:");
public static final long /*int*/ sel_appendBezierPathWithRoundedRect_xRadius_yRadius_ = sel_registerName("appendBezierPathWithRoundedRect:xRadius:yRadius:");
public static final long /*int*/ sel_appendString_ = sel_registerName("appendString:");
public static final long /*int*/ sel_application_openFile_ = sel_registerName("application:openFile:");
public static final long /*int*/ sel_application_openFiles_ = sel_registerName("application:openFiles:");
public static final long /*int*/ sel_applicationDidBecomeActive_ = sel_registerName("applicationDidBecomeActive:");
public static final long /*int*/ sel_applicationDidFinishLaunching_ = sel_registerName("applicationDidFinishLaunching:");
public static final long /*int*/ sel_applicationDidResignActive_ = sel_registerName("applicationDidResignActive:");
public static final long /*int*/ sel_applicationDockMenu_ = sel_registerName("applicationDockMenu:");
public static final long /*int*/ sel_applicationIconImage = sel_registerName("applicationIconImage");
public static final long /*int*/ sel_applicationShouldHandleReopen_hasVisibleWindows_ = sel_registerName("applicationShouldHandleReopen:hasVisibleWindows:");
public static final long /*int*/ sel_applicationShouldTerminate_ = sel_registerName("applicationShouldTerminate:");
public static final long /*int*/ sel_applicationWillFinishLaunching_ = sel_registerName("applicationWillFinishLaunching:");
public static final long /*int*/ sel_applicationWillTerminate_ = sel_registerName("applicationWillTerminate:");
public static final long /*int*/ sel_archivedDataWithRootObject_ = sel_registerName("archivedDataWithRootObject:");
public static final long /*int*/ sel_areCursorRectsEnabled = sel_registerName("areCursorRectsEnabled");
public static final long /*int*/ sel_arrangeInFront_ = sel_registerName("arrangeInFront:");
public static final long /*int*/ sel_array = sel_registerName("array");
public static final long /*int*/ sel_arrayWithCapacity_ = sel_registerName("arrayWithCapacity:");
public static final long /*int*/ sel_arrayWithObject_ = sel_registerName("arrayWithObject:");
public static final long /*int*/ sel_arrowCursor = sel_registerName("arrowCursor");
public static final long /*int*/ sel_ascender = sel_registerName("ascender");
public static final long /*int*/ sel_attachColorList_ = sel_registerName("attachColorList:");
public static final long /*int*/ sel_attribute_atIndex_effectiveRange_ = sel_registerName("attribute:atIndex:effectiveRange:");
public static final long /*int*/ sel_attributedStringValue = sel_registerName("attributedStringValue");
public static final long /*int*/ sel_attributedStringWithAttachment_ = sel_registerName("attributedStringWithAttachment:");
public static final long /*int*/ sel_attributedSubstringFromRange_ = sel_registerName("attributedSubstringFromRange:");
public static final long /*int*/ sel_attributedTitle = sel_registerName("attributedTitle");
public static final long /*int*/ sel_attributesAtIndex_longestEffectiveRange_inRange_ = sel_registerName("attributesAtIndex:longestEffectiveRange:inRange:");
public static final long /*int*/ sel_autorelease = sel_registerName("autorelease");
public static final long /*int*/ sel_availableFontFamilies = sel_registerName("availableFontFamilies");
public static final long /*int*/ sel_availableFonts = sel_registerName("availableFonts");
public static final long /*int*/ sel_availableMembersOfFontFamily_ = sel_registerName("availableMembersOfFontFamily:");
public static final long /*int*/ sel_availableTypeFromArray_ = sel_registerName("availableTypeFromArray:");
public static final long /*int*/ sel_backgroundColor = sel_registerName("backgroundColor");
public static final long /*int*/ sel_backingScaleFactor = sel_registerName("backingScaleFactor");
public static final long /*int*/ sel_badgeLabel = sel_registerName("badgeLabel");
public static final long /*int*/ sel_baselineOffsetInLayoutManager_glyphIndex_ = sel_registerName("baselineOffsetInLayoutManager:glyphIndex:");
public static final long /*int*/ sel_becomeFirstResponder = sel_registerName("becomeFirstResponder");
public static final long /*int*/ sel_becomeKeyWindow = sel_registerName("becomeKeyWindow");
public static final long /*int*/ sel_beginDocument = sel_registerName("beginDocument");
public static final long /*int*/ sel_beginEditing = sel_registerName("beginEditing");
public static final long /*int*/ sel_beginGestureWithEvent_ = sel_registerName("beginGestureWithEvent:");
public static final long /*int*/ sel_beginPageInRect_atPlacement_ = sel_registerName("beginPageInRect:atPlacement:");
public static final long /*int*/ sel_beginSheet_modalForWindow_modalDelegate_didEndSelector_contextInfo_ = sel_registerName("beginSheet:modalForWindow:modalDelegate:didEndSelector:contextInfo:");
public static final long /*int*/ sel_beginSheetForWindow_modalDelegate_didEndSelector_contextInfo_trust_message_ = sel_registerName("beginSheetForWindow:modalDelegate:didEndSelector:contextInfo:trust:message:");
public static final long /*int*/ sel_beginSheetModalForWindow_modalDelegate_didEndSelector_contextInfo_ = sel_registerName("beginSheetModalForWindow:modalDelegate:didEndSelector:contextInfo:");
public static final long /*int*/ sel_beginSheetWithPrintInfo_modalForWindow_delegate_didEndSelector_contextInfo_ = sel_registerName("beginSheetWithPrintInfo:modalForWindow:delegate:didEndSelector:contextInfo:");
public static final long /*int*/ sel_bestRepresentationForDevice_ = sel_registerName("bestRepresentationForDevice:");
public static final long /*int*/ sel_bezierPath = sel_registerName("bezierPath");
public static final long /*int*/ sel_bezierPathByFlatteningPath = sel_registerName("bezierPathByFlatteningPath");
public static final long /*int*/ sel_bezierPathWithRect_ = sel_registerName("bezierPathWithRect:");
public static final long /*int*/ sel_bitmapData = sel_registerName("bitmapData");
public static final long /*int*/ sel_bitmapFormat = sel_registerName("bitmapFormat");
public static final long /*int*/ sel_bitsPerPixel = sel_registerName("bitsPerPixel");
public static final long /*int*/ sel_bitsPerSample = sel_registerName("bitsPerSample");
public static final long /*int*/ sel_blackColor = sel_registerName("blackColor");
public static final long /*int*/ sel_blueComponent = sel_registerName("blueComponent");
public static final long /*int*/ sel_boolValue = sel_registerName("boolValue");
public static final long /*int*/ sel_borderWidth = sel_registerName("borderWidth");
public static final long /*int*/ sel_boundingRectForGlyphRange_inTextContainer_ = sel_registerName("boundingRectForGlyphRange:inTextContainer:");
public static final long /*int*/ sel_boundingRectWithSize_options_ = sel_registerName("boundingRectWithSize:options:");
public static final long /*int*/ sel_bounds = sel_registerName("bounds");
public static final long /*int*/ sel_bundleIdentifier = sel_registerName("bundleIdentifier");
public static final long /*int*/ sel_bundlePath = sel_registerName("bundlePath");
public static final long /*int*/ sel_bundleWithIdentifier_ = sel_registerName("bundleWithIdentifier:");
public static final long /*int*/ sel_bundleWithPath_ = sel_registerName("bundleWithPath:");
public static final long /*int*/ sel_button = sel_registerName("button");
public static final long /*int*/ sel_buttonNumber = sel_registerName("buttonNumber");
public static final long /*int*/ sel_bytes = sel_registerName("bytes");
public static final long /*int*/ sel_bytesPerPlane = sel_registerName("bytesPerPlane");
public static final long /*int*/ sel_bytesPerRow = sel_registerName("bytesPerRow");
public static final long /*int*/ sel_calendarDate = sel_registerName("calendarDate");
public static final long /*int*/ sel_canBecomeKeyView = sel_registerName("canBecomeKeyView");
public static final long /*int*/ sel_canBecomeKeyWindow = sel_registerName("canBecomeKeyWindow");
public static final long /*int*/ sel_canDragRowsWithIndexes_atPoint_ = sel_registerName("canDragRowsWithIndexes:atPoint:");
public static final long /*int*/ sel_canGoBack = sel_registerName("canGoBack");
public static final long /*int*/ sel_canGoForward = sel_registerName("canGoForward");
public static final long /*int*/ sel_canRedo = sel_registerName("canRedo");
public static final long /*int*/ sel_canShowMIMEType_ = sel_registerName("canShowMIMEType:");
public static final long /*int*/ sel_canUndo = sel_registerName("canUndo");
public static final long /*int*/ sel_cancel = sel_registerName("cancel");
public static final long /*int*/ sel_cancelAuthenticationChallenge_ = sel_registerName("cancelAuthenticationChallenge:");
public static final long /*int*/ sel_cancelButtonCell = sel_registerName("cancelButtonCell");
public static final long /*int*/ sel_cancelOperation_ = sel_registerName("cancelOperation:");
public static final long /*int*/ sel_cancelTracking = sel_registerName("cancelTracking");
public static final long /*int*/ sel_cascadeTopLeftFromPoint_ = sel_registerName("cascadeTopLeftFromPoint:");
public static final long /*int*/ sel_cell = sel_registerName("cell");
public static final long /*int*/ sel_cellClass = sel_registerName("cellClass");
public static final long /*int*/ sel_cellSize = sel_registerName("cellSize");
public static final long /*int*/ sel_cellSizeForBounds_ = sel_registerName("cellSizeForBounds:");
public static final long /*int*/ sel_changeColor_ = sel_registerName("changeColor:");
public static final long /*int*/ sel_changeFont_ = sel_registerName("changeFont:");
public static final long /*int*/ sel_charCode = sel_registerName("charCode");
public static final long /*int*/ sel_characterAtIndex_ = sel_registerName("characterAtIndex:");
public static final long /*int*/ sel_characterIndexForGlyphAtIndex_ = sel_registerName("characterIndexForGlyphAtIndex:");
public static final long /*int*/ sel_characterIndexForInsertionAtPoint_ = sel_registerName("characterIndexForInsertionAtPoint:");
public static final long /*int*/ sel_characterIndexForPoint_ = sel_registerName("characterIndexForPoint:");
public static final long /*int*/ sel_characterIsMember_ = sel_registerName("characterIsMember:");
public static final long /*int*/ sel_characters = sel_registerName("characters");
public static final long /*int*/ sel_charactersIgnoringModifiers = sel_registerName("charactersIgnoringModifiers");
public static final long /*int*/ sel_chooseFilename_ = sel_registerName("chooseFilename:");
public static final long /*int*/ sel_className = sel_registerName("className");
public static final long /*int*/ sel_cleanUpOperation = sel_registerName("cleanUpOperation");
public static final long /*int*/ sel_clearColor = sel_registerName("clearColor");
public static final long /*int*/ sel_clearCurrentContext = sel_registerName("clearCurrentContext");
public static final long /*int*/ sel_clearDrawable = sel_registerName("clearDrawable");
public static final long /*int*/ sel_clickCount = sel_registerName("clickCount");
public static final long /*int*/ sel_clickedColumn = sel_registerName("clickedColumn");
public static final long /*int*/ sel_clickedRow = sel_registerName("clickedRow");
public static final long /*int*/ sel_close = sel_registerName("close");
public static final long /*int*/ sel_closePath = sel_registerName("closePath");
public static final long /*int*/ sel_code = sel_registerName("code");
public static final long /*int*/ sel_collapseItem_ = sel_registerName("collapseItem:");
public static final long /*int*/ sel_collapseItem_collapseChildren_ = sel_registerName("collapseItem:collapseChildren:");
public static final long /*int*/ sel_collectionBehavior = sel_registerName("collectionBehavior");
public static final long /*int*/ sel_color = sel_registerName("color");
public static final long /*int*/ sel_colorAtX_y_ = sel_registerName("colorAtX:y:");
public static final long /*int*/ sel_colorListNamed_ = sel_registerName("colorListNamed:");
public static final long /*int*/ sel_colorSpace = sel_registerName("colorSpace");
public static final long /*int*/ sel_colorSpaceModel = sel_registerName("colorSpaceModel");
public static final long /*int*/ sel_colorSpaceName = sel_registerName("colorSpaceName");
public static final long /*int*/ sel_colorUsingColorSpaceName_ = sel_registerName("colorUsingColorSpaceName:");
public static final long /*int*/ sel_colorWithCalibratedRed_green_blue_alpha_ = sel_registerName("colorWithCalibratedRed:green:blue:alpha:");
public static final long /*int*/ sel_colorWithDeviceRed_green_blue_alpha_ = sel_registerName("colorWithDeviceRed:green:blue:alpha:");
public static final long /*int*/ sel_colorWithKey_ = sel_registerName("colorWithKey:");
public static final long /*int*/ sel_colorWithPatternImage_ = sel_registerName("colorWithPatternImage:");
public static final long /*int*/ sel_columnAtPoint_ = sel_registerName("columnAtPoint:");
public static final long /*int*/ sel_columnIndexesInRect_ = sel_registerName("columnIndexesInRect:");
public static final long /*int*/ sel_columnWithIdentifier_ = sel_registerName("columnWithIdentifier:");
public static final long /*int*/ sel_comboBoxSelectionDidChange_ = sel_registerName("comboBoxSelectionDidChange:");
public static final long /*int*/ sel_comboBoxWillDismiss_ = sel_registerName("comboBoxWillDismiss:");
public static final long /*int*/ sel_comboBoxWillPopUp_ = sel_registerName("comboBoxWillPopUp:");
public static final long /*int*/ sel_compare_ = sel_registerName("compare:");
public static final long /*int*/ sel_concat = sel_registerName("concat");
public static final long /*int*/ sel_conformsToProtocol_ = sel_registerName("conformsToProtocol:");
public static final long /*int*/ sel_containerSize = sel_registerName("containerSize");
public static final long /*int*/ sel_containsIndex_ = sel_registerName("containsIndex:");
public static final long /*int*/ sel_containsObject_ = sel_registerName("containsObject:");
public static final long /*int*/ sel_containsPoint_ = sel_registerName("containsPoint:");
public static final long /*int*/ sel_contentRect = sel_registerName("contentRect");
public static final long /*int*/ sel_contentSize = sel_registerName("contentSize");
public static final long /*int*/ sel_contentSizeForFrameSize_hasHorizontalScroller_hasVerticalScroller_borderType_ = sel_registerName("contentSizeForFrameSize:hasHorizontalScroller:hasVerticalScroller:borderType:");
public static final long /*int*/ sel_contentView = sel_registerName("contentView");
public static final long /*int*/ sel_contentViewMargins = sel_registerName("contentViewMargins");
public static final long /*int*/ sel_context = sel_registerName("context");
public static final long /*int*/ sel_controlBackgroundColor = sel_registerName("controlBackgroundColor");
public static final long /*int*/ sel_controlContentFontOfSize_ = sel_registerName("controlContentFontOfSize:");
public static final long /*int*/ sel_controlDarkShadowColor = sel_registerName("controlDarkShadowColor");
public static final long /*int*/ sel_controlHighlightColor = sel_registerName("controlHighlightColor");
public static final long /*int*/ sel_controlLightHighlightColor = sel_registerName("controlLightHighlightColor");
public static final long /*int*/ sel_controlPointBounds = sel_registerName("controlPointBounds");
public static final long /*int*/ sel_controlShadowColor = sel_registerName("controlShadowColor");
public static final long /*int*/ sel_controlSize = sel_registerName("controlSize");
public static final long /*int*/ sel_controlTextColor = sel_registerName("controlTextColor");
public static final long /*int*/ sel_convertBaseToScreen_ = sel_registerName("convertBaseToScreen:");
public static final long /*int*/ sel_convertFont_toHaveTrait_ = sel_registerName("convertFont:toHaveTrait:");
public static final long /*int*/ sel_convertPoint_fromView_ = sel_registerName("convertPoint:fromView:");
public static final long /*int*/ sel_convertPoint_toView_ = sel_registerName("convertPoint:toView:");
public static final long /*int*/ sel_convertPointFromBase_ = sel_registerName("convertPointFromBase:");
public static final long /*int*/ sel_convertPointToBase_ = sel_registerName("convertPointToBase:");
public static final long /*int*/ sel_convertRect_fromView_ = sel_registerName("convertRect:fromView:");
public static final long /*int*/ sel_convertRect_toView_ = sel_registerName("convertRect:toView:");
public static final long /*int*/ sel_convertRectFromBase_ = sel_registerName("convertRectFromBase:");
public static final long /*int*/ sel_convertRectToBase_ = sel_registerName("convertRectToBase:");
public static final long /*int*/ sel_convertScreenToBase_ = sel_registerName("convertScreenToBase:");
public static final long /*int*/ sel_convertSize_fromView_ = sel_registerName("convertSize:fromView:");
public static final long /*int*/ sel_convertSize_toView_ = sel_registerName("convertSize:toView:");
public static final long /*int*/ sel_convertSizeFromBase_ = sel_registerName("convertSizeFromBase:");
public static final long /*int*/ sel_convertSizeToBase_ = sel_registerName("convertSizeToBase:");
public static final long /*int*/ sel_cookies = sel_registerName("cookies");
public static final long /*int*/ sel_cookiesForURL_ = sel_registerName("cookiesForURL:");
public static final long /*int*/ sel_cookiesWithResponseHeaderFields_forURL_ = sel_registerName("cookiesWithResponseHeaderFields:forURL:");
public static final long /*int*/ sel_copiesOnScroll = sel_registerName("copiesOnScroll");
public static final long /*int*/ sel_copy = sel_registerName("copy");
public static final long /*int*/ sel_copy_ = sel_registerName("copy:");
public static final long /*int*/ sel_count = sel_registerName("count");
public static final long /*int*/ sel_createContext = sel_registerName("createContext");
public static final long /*int*/ sel_createFileAtPath_contents_attributes_ = sel_registerName("createFileAtPath:contents:attributes:");
public static final long /*int*/ sel_credentialWithUser_password_persistence_ = sel_registerName("credentialWithUser:password:persistence:");
public static final long /*int*/ sel_crosshairCursor = sel_registerName("crosshairCursor");
public static final long /*int*/ sel_ctrlKey = sel_registerName("ctrlKey");
public static final long /*int*/ sel_currentContext = sel_registerName("currentContext");
public static final long /*int*/ sel_currentCursor = sel_registerName("currentCursor");
public static final long /*int*/ sel_currentEditor = sel_registerName("currentEditor");
public static final long /*int*/ sel_currentEvent = sel_registerName("currentEvent");
public static final long /*int*/ sel_currentHandler = sel_registerName("currentHandler");
public static final long /*int*/ sel_currentInputManager = sel_registerName("currentInputManager");
public static final long /*int*/ sel_currentPoint = sel_registerName("currentPoint");
public static final long /*int*/ sel_currentRunLoop = sel_registerName("currentRunLoop");
public static final long /*int*/ sel_currentThread = sel_registerName("currentThread");
public static final long /*int*/ sel_cursorUpdate_ = sel_registerName("cursorUpdate:");
public static final long /*int*/ sel_curveToPoint_controlPoint1_controlPoint2_ = sel_registerName("curveToPoint:controlPoint1:controlPoint2:");
public static final long /*int*/ sel_cut_ = sel_registerName("cut:");
public static final long /*int*/ sel_dataCell = sel_registerName("dataCell");
public static final long /*int*/ sel_dataForType_ = sel_registerName("dataForType:");
public static final long /*int*/ sel_dataSource = sel_registerName("dataSource");
public static final long /*int*/ sel_dataWithBytes_length_ = sel_registerName("dataWithBytes:length:");
public static final long /*int*/ sel_dateValue = sel_registerName("dateValue");
public static final long /*int*/ sel_dateWithCalendarFormat_timeZone_ = sel_registerName("dateWithCalendarFormat:timeZone:");
public static final long /*int*/ sel_dateWithTimeIntervalSinceNow_ = sel_registerName("dateWithTimeIntervalSinceNow:");
public static final long /*int*/ sel_dateWithYear_month_day_hour_minute_second_timeZone_ = sel_registerName("dateWithYear:month:day:hour:minute:second:timeZone:");
public static final long /*int*/ sel_dayOfMonth = sel_registerName("dayOfMonth");
public static final long /*int*/ sel_dealloc = sel_registerName("dealloc");
public static final long /*int*/ sel_decimalDigitCharacterSet = sel_registerName("decimalDigitCharacterSet");
public static final long /*int*/ sel_decimalSeparator = sel_registerName("decimalSeparator");
public static final long /*int*/ sel_declareTypes_owner_ = sel_registerName("declareTypes:owner:");
public static final long /*int*/ sel_defaultBaselineOffsetForFont_ = sel_registerName("defaultBaselineOffsetForFont:");
public static final long /*int*/ sel_defaultButtonCell = sel_registerName("defaultButtonCell");
public static final long /*int*/ sel_defaultCenter = sel_registerName("defaultCenter");
public static final long /*int*/ sel_defaultFlatness = sel_registerName("defaultFlatness");
public static final long /*int*/ sel_defaultLineHeightForFont_ = sel_registerName("defaultLineHeightForFont:");
public static final long /*int*/ sel_defaultManager = sel_registerName("defaultManager");
public static final long /*int*/ sel_defaultParagraphStyle = sel_registerName("defaultParagraphStyle");
public static final long /*int*/ sel_defaultPrinter = sel_registerName("defaultPrinter");
public static final long /*int*/ sel_defaultTimeZone = sel_registerName("defaultTimeZone");
public static final long /*int*/ sel_delegate = sel_registerName("delegate");
public static final long /*int*/ sel_deleteCookie_ = sel_registerName("deleteCookie:");
public static final long /*int*/ sel_deliverResult = sel_registerName("deliverResult");
public static final long /*int*/ sel_deltaX = sel_registerName("deltaX");
public static final long /*int*/ sel_deltaY = sel_registerName("deltaY");
public static final long /*int*/ sel_deminiaturize_ = sel_registerName("deminiaturize:");
public static final long /*int*/ sel_depth = sel_registerName("depth");
public static final long /*int*/ sel_descender = sel_registerName("descender");
public static final long /*int*/ sel_description = sel_registerName("description");
public static final long /*int*/ sel_deselectAll_ = sel_registerName("deselectAll:");
public static final long /*int*/ sel_deselectItemAtIndex_ = sel_registerName("deselectItemAtIndex:");
public static final long /*int*/ sel_deselectRow_ = sel_registerName("deselectRow:");
public static final long /*int*/ sel_destroyContext = sel_registerName("destroyContext");
public static final long /*int*/ sel_detail = sel_registerName("detail");
public static final long /*int*/ sel_device = sel_registerName("device");
public static final long /*int*/ sel_deviceDescription = sel_registerName("deviceDescription");
public static final long /*int*/ sel_deviceSize = sel_registerName("deviceSize");
public static final long /*int*/ sel_dictionary = sel_registerName("dictionary");
public static final long /*int*/ sel_dictionaryWithCapacity_ = sel_registerName("dictionaryWithCapacity:");
public static final long /*int*/ sel_dictionaryWithObject_forKey_ = sel_registerName("dictionaryWithObject:forKey:");
public static final long /*int*/ sel_disableCursorRects = sel_registerName("disableCursorRects");
public static final long /*int*/ sel_disableFlushWindow = sel_registerName("disableFlushWindow");
public static final long /*int*/ sel_disabledControlTextColor = sel_registerName("disabledControlTextColor");
public static final long /*int*/ sel_discardCursorRects = sel_registerName("discardCursorRects");
public static final long /*int*/ sel_display = sel_registerName("display");
public static final long /*int*/ sel_displayIfNeeded = sel_registerName("displayIfNeeded");
public static final long /*int*/ sel_displayName = sel_registerName("displayName");
public static final long /*int*/ sel_displayNameForKey_value_ = sel_registerName("displayNameForKey:value:");
public static final long /*int*/ sel_displayRectIgnoringOpacity_inContext_ = sel_registerName("displayRectIgnoringOpacity:inContext:");
public static final long /*int*/ sel_distantFuture = sel_registerName("distantFuture");
public static final long /*int*/ sel_doCommandBySelector_ = sel_registerName("doCommandBySelector:");
public static final long /*int*/ sel_dockTile = sel_registerName("dockTile");
public static final long /*int*/ sel_documentCursor = sel_registerName("documentCursor");
public static final long /*int*/ sel_documentSource = sel_registerName("documentSource");
public static final long /*int*/ sel_documentView = sel_registerName("documentView");
public static final long /*int*/ sel_documentViewShouldHandlePrint = sel_registerName("documentViewShouldHandlePrint");
public static final long /*int*/ sel_documentVisibleRect = sel_registerName("documentVisibleRect");
public static final long /*int*/ sel_doubleClickAtIndex_ = sel_registerName("doubleClickAtIndex:");
public static final long /*int*/ sel_doubleValue = sel_registerName("doubleValue");
public static final long /*int*/ sel_download = sel_registerName("download");
public static final long /*int*/ sel_download_decideDestinationWithSuggestedFilename_ = sel_registerName("download:decideDestinationWithSuggestedFilename:");
public static final long /*int*/ sel_dragImage_at_offset_event_pasteboard_source_slideBack_ = sel_registerName("dragImage:at:offset:event:pasteboard:source:slideBack:");
public static final long /*int*/ sel_dragImageForRowsWithIndexes_tableColumns_event_offset_ = sel_registerName("dragImageForRowsWithIndexes:tableColumns:event:offset:");
public static final long /*int*/ sel_dragSelectionWithEvent_offset_slideBack_ = sel_registerName("dragSelectionWithEvent:offset:slideBack:");
public static final long /*int*/ sel_draggedImage_beganAt_ = sel_registerName("draggedImage:beganAt:");
public static final long /*int*/ sel_draggedImage_endedAt_operation_ = sel_registerName("draggedImage:endedAt:operation:");
public static final long /*int*/ sel_draggingDestinationWindow = sel_registerName("draggingDestinationWindow");
public static final long /*int*/ sel_draggingEnded_ = sel_registerName("draggingEnded:");
public static final long /*int*/ sel_draggingEntered_ = sel_registerName("draggingEntered:");
public static final long /*int*/ sel_draggingExited_ = sel_registerName("draggingExited:");
public static final long /*int*/ sel_draggingLocation = sel_registerName("draggingLocation");
public static final long /*int*/ sel_draggingPasteboard = sel_registerName("draggingPasteboard");
public static final long /*int*/ sel_draggingSourceOperationMask = sel_registerName("draggingSourceOperationMask");
public static final long /*int*/ sel_draggingSourceOperationMaskForLocal_ = sel_registerName("draggingSourceOperationMaskForLocal:");
public static final long /*int*/ sel_draggingUpdated_ = sel_registerName("draggingUpdated:");
public static final long /*int*/ sel_drawAtPoint_ = sel_registerName("drawAtPoint:");
public static final long /*int*/ sel_drawAtPoint_fromRect_operation_fraction_ = sel_registerName("drawAtPoint:fromRect:operation:fraction:");
public static final long /*int*/ sel_drawBackgroundForGlyphRange_atPoint_ = sel_registerName("drawBackgroundForGlyphRange:atPoint:");
public static final long /*int*/ sel_drawBackgroundInClipRect_ = sel_registerName("drawBackgroundInClipRect:");
public static final long /*int*/ sel_drawFromPoint_toPoint_options_ = sel_registerName("drawFromPoint:toPoint:options:");
public static final long /*int*/ sel_drawGlyphsForGlyphRange_atPoint_ = sel_registerName("drawGlyphsForGlyphRange:atPoint:");
public static final long /*int*/ sel_drawImage_withFrame_inView_ = sel_registerName("drawImage:withFrame:inView:");
public static final long /*int*/ sel_drawInRect_ = sel_registerName("drawInRect:");
public static final long /*int*/ sel_drawInRect_angle_ = sel_registerName("drawInRect:angle:");
public static final long /*int*/ sel_drawInRect_fromRect_operation_fraction_ = sel_registerName("drawInRect:fromRect:operation:fraction:");
public static final long /*int*/ sel_drawInteriorWithFrame_inView_ = sel_registerName("drawInteriorWithFrame:inView:");
public static final long /*int*/ sel_drawLabel_inRect_ = sel_registerName("drawLabel:inRect:");
public static final long /*int*/ sel_drawRect_ = sel_registerName("drawRect:");
public static final long /*int*/ sel_drawSortIndicatorWithFrame_inView_ascending_priority_ = sel_registerName("drawSortIndicatorWithFrame:inView:ascending:priority:");
public static final long /*int*/ sel_drawStatusBarBackgroundInRect_withHighlight_ = sel_registerName("drawStatusBarBackgroundInRect:withHighlight:");
public static final long /*int*/ sel_drawTitle_withFrame_inView_ = sel_registerName("drawTitle:withFrame:inView:");
public static final long /*int*/ sel_drawViewBackgroundInRect_ = sel_registerName("drawViewBackgroundInRect:");
public static final long /*int*/ sel_drawWithExpansionFrame_inView_ = sel_registerName("drawWithExpansionFrame:inView:");
public static final long /*int*/ sel_drawingRectForBounds_ = sel_registerName("drawingRectForBounds:");
public static final long /*int*/ sel_elementAtIndex_associatedPoints_ = sel_registerName("elementAtIndex:associatedPoints:");
public static final long /*int*/ sel_elementCount = sel_registerName("elementCount");
public static final long /*int*/ sel_enableCursorRects = sel_registerName("enableCursorRects");
public static final long /*int*/ sel_enableFlushWindow = sel_registerName("enableFlushWindow");
public static final long /*int*/ sel_endDocument = sel_registerName("endDocument");
public static final long /*int*/ sel_endEditing = sel_registerName("endEditing");
public static final long /*int*/ sel_endEditingFor_ = sel_registerName("endEditingFor:");
public static final long /*int*/ sel_endGestureWithEvent_ = sel_registerName("endGestureWithEvent:");
public static final long /*int*/ sel_endPage = sel_registerName("endPage");
public static final long /*int*/ sel_endSheet_returnCode_ = sel_registerName("endSheet:returnCode:");
public static final long /*int*/ sel_enterExitEventWithType_location_modifierFlags_timestamp_windowNumber_context_eventNumber_trackingNumber_userData_ = sel_registerName("enterExitEventWithType:location:modifierFlags:timestamp:windowNumber:context:eventNumber:trackingNumber:userData:");
public static final long /*int*/ sel_enumeratorAtPath_ = sel_registerName("enumeratorAtPath:");
public static final long /*int*/ sel_expandItem_ = sel_registerName("expandItem:");
public static final long /*int*/ sel_expandItem_expandChildren_ = sel_registerName("expandItem:expandChildren:");
public static final long /*int*/ sel_expansionFrameWithFrame_inView_ = sel_registerName("expansionFrameWithFrame:inView:");
public static final long /*int*/ sel_familyName = sel_registerName("familyName");
public static final long /*int*/ sel_fieldEditor_forObject_ = sel_registerName("fieldEditor:forObject:");
public static final long /*int*/ sel_fileExistsAtPath_ = sel_registerName("fileExistsAtPath:");
public static final long /*int*/ sel_fileExistsAtPath_isDirectory_ = sel_registerName("fileExistsAtPath:isDirectory:");
public static final long /*int*/ sel_fileSystemRepresentation = sel_registerName("fileSystemRepresentation");
public static final long /*int*/ sel_fileURLWithPath_ = sel_registerName("fileURLWithPath:");
public static final long /*int*/ sel_filename = sel_registerName("filename");
public static final long /*int*/ sel_filenames = sel_registerName("filenames");
public static final long /*int*/ sel_fill = sel_registerName("fill");
public static final long /*int*/ sel_fillRect_ = sel_registerName("fillRect:");
public static final long /*int*/ sel_finishLaunching = sel_registerName("finishLaunching");
public static final long /*int*/ sel_firstIndex = sel_registerName("firstIndex");
public static final long /*int*/ sel_firstRectForCharacterRange_ = sel_registerName("firstRectForCharacterRange:");
public static final long /*int*/ sel_firstResponder = sel_registerName("firstResponder");
public static final long /*int*/ sel_flagsChanged_ = sel_registerName("flagsChanged:");
public static final long /*int*/ sel_floatValue = sel_registerName("floatValue");
public static final long /*int*/ sel_flushBuffer = sel_registerName("flushBuffer");
public static final long /*int*/ sel_flushGraphics = sel_registerName("flushGraphics");
public static final long /*int*/ sel_flushWindowIfNeeded = sel_registerName("flushWindowIfNeeded");
public static final long /*int*/ sel_font = sel_registerName("font");
public static final long /*int*/ sel_fontName = sel_registerName("fontName");
public static final long /*int*/ sel_fontWithFamily_traits_weight_size_ = sel_registerName("fontWithFamily:traits:weight:size:");
public static final long /*int*/ sel_fontWithName_size_ = sel_registerName("fontWithName:size:");
public static final long /*int*/ sel_frame = sel_registerName("frame");
public static final long /*int*/ sel_frameOfCellAtColumn_row_ = sel_registerName("frameOfCellAtColumn:row:");
public static final long /*int*/ sel_frameOfOutlineCellAtRow_ = sel_registerName("frameOfOutlineCellAtRow:");
public static final long /*int*/ sel_frameRectForContentRect_ = sel_registerName("frameRectForContentRect:");
public static final long /*int*/ sel_frameSizeForContentSize_hasHorizontalScroller_hasVerticalScroller_borderType_ = sel_registerName("frameSizeForContentSize:hasHorizontalScroller:hasVerticalScroller:borderType:");
public static final long /*int*/ sel_fullPathForApplication_ = sel_registerName("fullPathForApplication:");
public static final long /*int*/ sel_generalPasteboard = sel_registerName("generalPasteboard");
public static final long /*int*/ sel_genericRGBColorSpace = sel_registerName("genericRGBColorSpace");
public static final long /*int*/ sel_getBitmapDataPlanes_ = sel_registerName("getBitmapDataPlanes:");
public static final long /*int*/ sel_getBytes_ = sel_registerName("getBytes:");
public static final long /*int*/ sel_getBytes_length_ = sel_registerName("getBytes:length:");
public static final long /*int*/ sel_getCharacters_ = sel_registerName("getCharacters:");
public static final long /*int*/ sel_getCharacters_range_ = sel_registerName("getCharacters:range:");
public static final long /*int*/ sel_getComponents_ = sel_registerName("getComponents:");
public static final long /*int*/ sel_getGlyphs_range_ = sel_registerName("getGlyphs:range:");
public static final long /*int*/ sel_getGlyphsInRange_glyphs_characterIndexes_glyphInscriptions_elasticBits_bidiLevels_ = sel_registerName("getGlyphsInRange:glyphs:characterIndexes:glyphInscriptions:elasticBits:bidiLevels:");
public static final long /*int*/ sel_getIndexes_maxCount_inIndexRange_ = sel_registerName("getIndexes:maxCount:inIndexRange:");
public static final long /*int*/ sel_getInfoForFile_application_type_ = sel_registerName("getInfoForFile:application:type:");
public static final long /*int*/ sel_getValues_forAttribute_forVirtualScreen_ = sel_registerName("getValues:forAttribute:forVirtualScreen:");
public static final long /*int*/ sel_globalContext = sel_registerName("globalContext");
public static final long /*int*/ sel_glyphIndexForCharacterAtIndex_ = sel_registerName("glyphIndexForCharacterAtIndex:");
public static final long /*int*/ sel_glyphIndexForPoint_inTextContainer_fractionOfDistanceThroughGlyph_ = sel_registerName("glyphIndexForPoint:inTextContainer:fractionOfDistanceThroughGlyph:");
public static final long /*int*/ sel_glyphRangeForCharacterRange_actualCharacterRange_ = sel_registerName("glyphRangeForCharacterRange:actualCharacterRange:");
public static final long /*int*/ sel_glyphRangeForTextContainer_ = sel_registerName("glyphRangeForTextContainer:");
public static final long /*int*/ sel_goBack = sel_registerName("goBack");
public static final long /*int*/ sel_goForward = sel_registerName("goForward");
public static final long /*int*/ sel_graphicsContext = sel_registerName("graphicsContext");
public static final long /*int*/ sel_graphicsContextWithBitmapImageRep_ = sel_registerName("graphicsContextWithBitmapImageRep:");
public static final long /*int*/ sel_graphicsContextWithGraphicsPort_flipped_ = sel_registerName("graphicsContextWithGraphicsPort:flipped:");
public static final long /*int*/ sel_graphicsContextWithWindow_ = sel_registerName("graphicsContextWithWindow:");
public static final long /*int*/ sel_graphicsPort = sel_registerName("graphicsPort");
public static final long /*int*/ sel_greenComponent = sel_registerName("greenComponent");
public static final long /*int*/ sel_handleEvent_ = sel_registerName("handleEvent:");
public static final long /*int*/ sel_handleFailureInFunction_file_lineNumber_description_ = sel_registerName("handleFailureInFunction:file:lineNumber:description:");
public static final long /*int*/ sel_handleFailureInMethod_object_file_lineNumber_description_ = sel_registerName("handleFailureInMethod:object:file:lineNumber:description:");
public static final long /*int*/ sel_handleMouseEvent_ = sel_registerName("handleMouseEvent:");
public static final long /*int*/ sel_hasAlpha = sel_registerName("hasAlpha");
public static final long /*int*/ sel_hasMarkedText = sel_registerName("hasMarkedText");
public static final long /*int*/ sel_hasPassword = sel_registerName("hasPassword");
public static final long /*int*/ sel_hasShadow = sel_registerName("hasShadow");
public static final long /*int*/ sel_headerCell = sel_registerName("headerCell");
public static final long /*int*/ sel_headerRectOfColumn_ = sel_registerName("headerRectOfColumn:");
public static final long /*int*/ sel_headerView = sel_registerName("headerView");
public static final long /*int*/ sel_helpRequested_ = sel_registerName("helpRequested:");
public static final long /*int*/ sel_hide_ = sel_registerName("hide:");
public static final long /*int*/ sel_hideOtherApplications_ = sel_registerName("hideOtherApplications:");
public static final long /*int*/ sel_highlightColorInView_ = sel_registerName("highlightColorInView:");
public static final long /*int*/ sel_highlightColorWithFrame_inView_ = sel_registerName("highlightColorWithFrame:inView:");
public static final long /*int*/ sel_highlightSelectionInClipRect_ = sel_registerName("highlightSelectionInClipRect:");
public static final long /*int*/ sel_hitPart = sel_registerName("hitPart");
public static final long /*int*/ sel_hitTest_ = sel_registerName("hitTest:");
public static final long /*int*/ sel_hitTestForEvent_inRect_ofView_ = sel_registerName("hitTestForEvent:inRect:ofView:");
public static final long /*int*/ sel_host = sel_registerName("host");
public static final long /*int*/ sel_hotSpot = sel_registerName("hotSpot");
public static final long /*int*/ sel_hourOfDay = sel_registerName("hourOfDay");
public static final long /*int*/ sel_iconForFile_ = sel_registerName("iconForFile:");
public static final long /*int*/ sel_iconForFileType_ = sel_registerName("iconForFileType:");
public static final long /*int*/ sel_ignore = sel_registerName("ignore");
public static final long /*int*/ sel_ignoreModifierKeysWhileDragging = sel_registerName("ignoreModifierKeysWhileDragging");
public static final long /*int*/ sel_image = sel_registerName("image");
public static final long /*int*/ sel_imageInterpolation = sel_registerName("imageInterpolation");
public static final long /*int*/ sel_imageNamed_ = sel_registerName("imageNamed:");
public static final long /*int*/ sel_imageRectForBounds_ = sel_registerName("imageRectForBounds:");
public static final long /*int*/ sel_imageRepWithData_ = sel_registerName("imageRepWithData:");
public static final long /*int*/ sel_imageablePageBounds = sel_registerName("imageablePageBounds");
public static final long /*int*/ sel_increment = sel_registerName("increment");
public static final long /*int*/ sel_indentationPerLevel = sel_registerName("indentationPerLevel");
public static final long /*int*/ sel_indexOfItemWithTarget_andAction_ = sel_registerName("indexOfItemWithTarget:andAction:");
public static final long /*int*/ sel_indexOfObjectIdenticalTo_ = sel_registerName("indexOfObjectIdenticalTo:");
public static final long /*int*/ sel_indexOfSelectedItem = sel_registerName("indexOfSelectedItem");
public static final long /*int*/ sel_indexSetWithIndex_ = sel_registerName("indexSetWithIndex:");
public static final long /*int*/ sel_infoDictionary = sel_registerName("infoDictionary");
public static final long /*int*/ sel_init = sel_registerName("init");
public static final long /*int*/ sel_initByReferencingFile_ = sel_registerName("initByReferencingFile:");
public static final long /*int*/ sel_initListDescriptor = sel_registerName("initListDescriptor");
public static final long /*int*/ sel_initWithAttributes_ = sel_registerName("initWithAttributes:");
public static final long /*int*/ sel_initWithBitmapDataPlanes_pixelsWide_pixelsHigh_bitsPerSample_samplesPerPixel_hasAlpha_isPlanar_colorSpaceName_bitmapFormat_bytesPerRow_bitsPerPixel_ = sel_registerName("initWithBitmapDataPlanes:pixelsWide:pixelsHigh:bitsPerSample:samplesPerPixel:hasAlpha:isPlanar:colorSpaceName:bitmapFormat:bytesPerRow:bitsPerPixel:");
public static final long /*int*/ sel_initWithBitmapDataPlanes_pixelsWide_pixelsHigh_bitsPerSample_samplesPerPixel_hasAlpha_isPlanar_colorSpaceName_bytesPerRow_bitsPerPixel_ = sel_registerName("initWithBitmapDataPlanes:pixelsWide:pixelsHigh:bitsPerSample:samplesPerPixel:hasAlpha:isPlanar:colorSpaceName:bytesPerRow:bitsPerPixel:");
public static final long /*int*/ sel_initWithCapacity_ = sel_registerName("initWithCapacity:");
public static final long /*int*/ sel_initWithCharacters_length_ = sel_registerName("initWithCharacters:length:");
public static final long /*int*/ sel_initWithContainerSize_ = sel_registerName("initWithContainerSize:");
public static final long /*int*/ sel_initWithContentRect_styleMask_backing_defer_ = sel_registerName("initWithContentRect:styleMask:backing:defer:");
public static final long /*int*/ sel_initWithContentRect_styleMask_backing_defer_screen_ = sel_registerName("initWithContentRect:styleMask:backing:defer:screen:");
public static final long /*int*/ sel_initWithContentsOfFile_ = sel_registerName("initWithContentsOfFile:");
public static final long /*int*/ sel_initWithData_ = sel_registerName("initWithData:");
public static final long /*int*/ sel_initWithDictionary_ = sel_registerName("initWithDictionary:");
public static final long /*int*/ sel_initWithFileWrapper_ = sel_registerName("initWithFileWrapper:");
public static final long /*int*/ sel_initWithFocusedViewRect_ = sel_registerName("initWithFocusedViewRect:");
public static final long /*int*/ sel_initWithFormat_shareContext_ = sel_registerName("initWithFormat:shareContext:");
public static final long /*int*/ sel_initWithFrame_ = sel_registerName("initWithFrame:");
public static final long /*int*/ sel_initWithFrame_frameName_groupName_ = sel_registerName("initWithFrame:frameName:groupName:");
public static final long /*int*/ sel_initWithFrame_pullsDown_ = sel_registerName("initWithFrame:pullsDown:");
public static final long /*int*/ sel_initWithIconRef_ = sel_registerName("initWithIconRef:");
public static final long /*int*/ sel_initWithIdentifier_ = sel_registerName("initWithIdentifier:");
public static final long /*int*/ sel_initWithImage_hotSpot_ = sel_registerName("initWithImage:hotSpot:");
public static final long /*int*/ sel_initWithIndex_ = sel_registerName("initWithIndex:");
public static final long /*int*/ sel_initWithIndexSet_ = sel_registerName("initWithIndexSet:");
public static final long /*int*/ sel_initWithIndexesInRange_ = sel_registerName("initWithIndexesInRange:");
public static final long /*int*/ sel_initWithItemIdentifier_ = sel_registerName("initWithItemIdentifier:");
public static final long /*int*/ sel_initWithLocaleIdentifier_ = sel_registerName("initWithLocaleIdentifier:");
public static final long /*int*/ sel_initWithName_ = sel_registerName("initWithName:");
public static final long /*int*/ sel_initWithRect_options_owner_userInfo_ = sel_registerName("initWithRect:options:owner:userInfo:");
public static final long /*int*/ sel_initWithSize_ = sel_registerName("initWithSize:");
public static final long /*int*/ sel_initWithStartingColor_endingColor_ = sel_registerName("initWithStartingColor:endingColor:");
public static final long /*int*/ sel_initWithString_ = sel_registerName("initWithString:");
public static final long /*int*/ sel_initWithString_attributes_ = sel_registerName("initWithString:attributes:");
public static final long /*int*/ sel_initWithTitle_ = sel_registerName("initWithTitle:");
public static final long /*int*/ sel_initWithTitle_action_keyEquivalent_ = sel_registerName("initWithTitle:action:keyEquivalent:");
public static final long /*int*/ sel_initWithTransform_ = sel_registerName("initWithTransform:");
public static final long /*int*/ sel_initWithType_location_ = sel_registerName("initWithType:location:");
public static final long /*int*/ sel_initWithURL_ = sel_registerName("initWithURL:");
public static final long /*int*/ sel_insertColor_key_atIndex_ = sel_registerName("insertColor:key:atIndex:");
public static final long /*int*/ sel_insertItem_atIndex_ = sel_registerName("insertItem:atIndex:");
public static final long /*int*/ sel_insertItemWithItemIdentifier_atIndex_ = sel_registerName("insertItemWithItemIdentifier:atIndex:");
public static final long /*int*/ sel_insertItemWithObjectValue_atIndex_ = sel_registerName("insertItemWithObjectValue:atIndex:");
public static final long /*int*/ sel_insertTabViewItem_atIndex_ = sel_registerName("insertTabViewItem:atIndex:");
public static final long /*int*/ sel_insertText_ = sel_registerName("insertText:");
public static final long /*int*/ sel_intValue = sel_registerName("intValue");
public static final long /*int*/ sel_integerValue = sel_registerName("integerValue");
public static final long /*int*/ sel_intercellSpacing = sel_registerName("intercellSpacing");
public static final long /*int*/ sel_interpretKeyEvents_ = sel_registerName("interpretKeyEvents:");
public static final long /*int*/ sel_invalidate = sel_registerName("invalidate");
public static final long /*int*/ sel_invalidateShadow = sel_registerName("invalidateShadow");
public static final long /*int*/ sel_invert = sel_registerName("invert");
public static final long /*int*/ sel_isActive = sel_registerName("isActive");
public static final long /*int*/ sel_isDocumentEdited = sel_registerName("isDocumentEdited");
public static final long /*int*/ sel_isDrawingToScreen = sel_registerName("isDrawingToScreen");
public static final long /*int*/ sel_isEmpty = sel_registerName("isEmpty");
public static final long /*int*/ sel_isEnabled = sel_registerName("isEnabled");
public static final long /*int*/ sel_isEqual_ = sel_registerName("isEqual:");
public static final long /*int*/ sel_isEqualTo_ = sel_registerName("isEqualTo:");
public static final long /*int*/ sel_isEqualToString_ = sel_registerName("isEqualToString:");
public static final long /*int*/ sel_isExecutableFileAtPath_ = sel_registerName("isExecutableFileAtPath:");
public static final long /*int*/ sel_isFieldEditor = sel_registerName("isFieldEditor");
public static final long /*int*/ sel_isFilePackageAtPath_ = sel_registerName("isFilePackageAtPath:");
public static final long /*int*/ sel_isFileURL = sel_registerName("isFileURL");
public static final long /*int*/ sel_isFlipped = sel_registerName("isFlipped");
public static final long /*int*/ sel_isHidden = sel_registerName("isHidden");
public static final long /*int*/ sel_isHiddenOrHasHiddenAncestor = sel_registerName("isHiddenOrHasHiddenAncestor");
public static final long /*int*/ sel_isHighlighted = sel_registerName("isHighlighted");
public static final long /*int*/ sel_isItemExpanded_ = sel_registerName("isItemExpanded:");
public static final long /*int*/ sel_isKeyWindow = sel_registerName("isKeyWindow");
public static final long /*int*/ sel_isKindOfClass_ = sel_registerName("isKindOfClass:");
public static final long /*int*/ sel_isMainThread = sel_registerName("isMainThread");
public static final long /*int*/ sel_isMainWindow = sel_registerName("isMainWindow");
public static final long /*int*/ sel_isMiniaturized = sel_registerName("isMiniaturized");
public static final long /*int*/ sel_isOpaque = sel_registerName("isOpaque");
public static final long /*int*/ sel_isPlanar = sel_registerName("isPlanar");
public static final long /*int*/ sel_isResting = sel_registerName("isResting");
public static final long /*int*/ sel_isRowSelected_ = sel_registerName("isRowSelected:");
public static final long /*int*/ sel_isRunning = sel_registerName("isRunning");
public static final long /*int*/ sel_isSeparatorItem = sel_registerName("isSeparatorItem");
public static final long /*int*/ sel_isSessionOnly = sel_registerName("isSessionOnly");
public static final long /*int*/ sel_isSheet = sel_registerName("isSheet");
public static final long /*int*/ sel_isVisible = sel_registerName("isVisible");
public static final long /*int*/ sel_isZoomed = sel_registerName("isZoomed");
public static final long /*int*/ sel_itemArray = sel_registerName("itemArray");
public static final long /*int*/ sel_itemAtIndex_ = sel_registerName("itemAtIndex:");
public static final long /*int*/ sel_itemAtRow_ = sel_registerName("itemAtRow:");
public static final long /*int*/ sel_itemHeight = sel_registerName("itemHeight");
public static final long /*int*/ sel_itemIdentifier = sel_registerName("itemIdentifier");
public static final long /*int*/ sel_itemObjectValueAtIndex_ = sel_registerName("itemObjectValueAtIndex:");
public static final long /*int*/ sel_itemTitleAtIndex_ = sel_registerName("itemTitleAtIndex:");
public static final long /*int*/ sel_itemWithTag_ = sel_registerName("itemWithTag:");
public static final long /*int*/ sel_jobDisposition = sel_registerName("jobDisposition");
public static final long /*int*/ sel_keyCode = sel_registerName("keyCode");
public static final long /*int*/ sel_keyDown_ = sel_registerName("keyDown:");
public static final long /*int*/ sel_keyEquivalent = sel_registerName("keyEquivalent");
public static final long /*int*/ sel_keyEquivalentModifierMask = sel_registerName("keyEquivalentModifierMask");
public static final long /*int*/ sel_keyUp_ = sel_registerName("keyUp:");
public static final long /*int*/ sel_keyWindow = sel_registerName("keyWindow");
public static final long /*int*/ sel_knobProportion = sel_registerName("knobProportion");
public static final long /*int*/ sel_knobThickness = sel_registerName("knobThickness");
public static final long /*int*/ sel_lastPathComponent = sel_registerName("lastPathComponent");
public static final long /*int*/ sel_layoutManager = sel_registerName("layoutManager");
public static final long /*int*/ sel_leading = sel_registerName("leading");
public static final long /*int*/ sel_length = sel_registerName("length");
public static final long /*int*/ sel_level = sel_registerName("level");
public static final long /*int*/ sel_levelForItem_ = sel_registerName("levelForItem:");
public static final long /*int*/ sel_lineFragmentUsedRectForGlyphAtIndex_effectiveRange_ = sel_registerName("lineFragmentUsedRectForGlyphAtIndex:effectiveRange:");
public static final long /*int*/ sel_lineFragmentUsedRectForGlyphAtIndex_effectiveRange_withoutAdditionalLayout_ = sel_registerName("lineFragmentUsedRectForGlyphAtIndex:effectiveRange:withoutAdditionalLayout:");
public static final long /*int*/ sel_lineToPoint_ = sel_registerName("lineToPoint:");
public static final long /*int*/ sel_linkTextAttributes = sel_registerName("linkTextAttributes");
public static final long /*int*/ sel_loadHTMLString_baseURL_ = sel_registerName("loadHTMLString:baseURL:");
public static final long /*int*/ sel_loadNibFile_externalNameTable_withZone_ = sel_registerName("loadNibFile:externalNameTable:withZone:");
public static final long /*int*/ sel_loadRequest_ = sel_registerName("loadRequest:");
public static final long /*int*/ sel_localizedDescription = sel_registerName("localizedDescription");
public static final long /*int*/ sel_location = sel_registerName("location");
public static final long /*int*/ sel_locationForGlyphAtIndex_ = sel_registerName("locationForGlyphAtIndex:");
public static final long /*int*/ sel_locationInWindow = sel_registerName("locationInWindow");
public static final long /*int*/ sel_lockFocus = sel_registerName("lockFocus");
public static final long /*int*/ sel_lowercaseString = sel_registerName("lowercaseString");
public static final long /*int*/ sel_magnification = sel_registerName("magnification");
public static final long /*int*/ sel_magnifyWithEvent_ = sel_registerName("magnifyWithEvent:");
public static final long /*int*/ sel_mainBundle = sel_registerName("mainBundle");
public static final long /*int*/ sel_mainFrame = sel_registerName("mainFrame");
public static final long /*int*/ sel_mainMenu = sel_registerName("mainMenu");
public static final long /*int*/ sel_mainRunLoop = sel_registerName("mainRunLoop");
public static final long /*int*/ sel_mainScreen = sel_registerName("mainScreen");
public static final long /*int*/ sel_mainWindow = sel_registerName("mainWindow");
public static final long /*int*/ sel_makeCurrentContext = sel_registerName("makeCurrentContext");
public static final long /*int*/ sel_makeFirstResponder_ = sel_registerName("makeFirstResponder:");
public static final long /*int*/ sel_makeKeyAndOrderFront_ = sel_registerName("makeKeyAndOrderFront:");
public static final long /*int*/ sel_markedRange = sel_registerName("markedRange");
public static final long /*int*/ sel_markedTextAttributes = sel_registerName("markedTextAttributes");
public static final long /*int*/ sel_maxValue = sel_registerName("maxValue");
public static final long /*int*/ sel_maximum = sel_registerName("maximum");
public static final long /*int*/ sel_maximumFractionDigits = sel_registerName("maximumFractionDigits");
public static final long /*int*/ sel_maximumIntegerDigits = sel_registerName("maximumIntegerDigits");
public static final long /*int*/ sel_menu = sel_registerName("menu");
public static final long /*int*/ sel_menu_willHighlightItem_ = sel_registerName("menu:willHighlightItem:");
public static final long /*int*/ sel_menuBarFontOfSize_ = sel_registerName("menuBarFontOfSize:");
public static final long /*int*/ sel_menuDidClose_ = sel_registerName("menuDidClose:");
public static final long /*int*/ sel_menuFontOfSize_ = sel_registerName("menuFontOfSize:");
public static final long /*int*/ sel_menuForEvent_ = sel_registerName("menuForEvent:");
public static final long /*int*/ sel_menuNeedsUpdate_ = sel_registerName("menuNeedsUpdate:");
public static final long /*int*/ sel_menuWillOpen_ = sel_registerName("menuWillOpen:");
public static final long /*int*/ sel_metaKey = sel_registerName("metaKey");
public static final long /*int*/ sel_minFrameWidthWithTitle_styleMask_ = sel_registerName("minFrameWidthWithTitle:styleMask:");
public static final long /*int*/ sel_minSize = sel_registerName("minSize");
public static final long /*int*/ sel_minValue = sel_registerName("minValue");
public static final long /*int*/ sel_miniaturize_ = sel_registerName("miniaturize:");
public static final long /*int*/ sel_minimum = sel_registerName("minimum");
public static final long /*int*/ sel_minimumSize = sel_registerName("minimumSize");
public static final long /*int*/ sel_minuteOfHour = sel_registerName("minuteOfHour");
public static final long /*int*/ sel_modifierFlags = sel_registerName("modifierFlags");
public static final long /*int*/ sel_monthOfYear = sel_registerName("monthOfYear");
public static final long /*int*/ sel_mouse_inRect_ = sel_registerName("mouse:inRect:");
public static final long /*int*/ sel_mouseDown_ = sel_registerName("mouseDown:");
public static final long /*int*/ sel_mouseDownCanMoveWindow = sel_registerName("mouseDownCanMoveWindow");
public static final long /*int*/ sel_mouseDragged_ = sel_registerName("mouseDragged:");
public static final long /*int*/ sel_mouseEntered_ = sel_registerName("mouseEntered:");
public static final long /*int*/ sel_mouseExited_ = sel_registerName("mouseExited:");
public static final long /*int*/ sel_mouseLocation = sel_registerName("mouseLocation");
public static final long /*int*/ sel_mouseLocationOutsideOfEventStream = sel_registerName("mouseLocationOutsideOfEventStream");
public static final long /*int*/ sel_mouseMoved_ = sel_registerName("mouseMoved:");
public static final long /*int*/ sel_mouseUp_ = sel_registerName("mouseUp:");
public static final long /*int*/ sel_moveColumn_toColumn_ = sel_registerName("moveColumn:toColumn:");
public static final long /*int*/ sel_moveToBeginningOfParagraph_ = sel_registerName("moveToBeginningOfParagraph:");
public static final long /*int*/ sel_moveToEndOfParagraph_ = sel_registerName("moveToEndOfParagraph:");
public static final long /*int*/ sel_moveToPoint_ = sel_registerName("moveToPoint:");
public static final long /*int*/ sel_moveUp_ = sel_registerName("moveUp:");
public static final long /*int*/ sel_mutableCopy = sel_registerName("mutableCopy");
public static final long /*int*/ sel_mutableString = sel_registerName("mutableString");
public static final long /*int*/ sel_name = sel_registerName("name");
public static final long /*int*/ sel_needsPanelToBecomeKey = sel_registerName("needsPanelToBecomeKey");
public static final long /*int*/ sel_nextEventMatchingMask_untilDate_inMode_dequeue_ = sel_registerName("nextEventMatchingMask:untilDate:inMode:dequeue:");
public static final long /*int*/ sel_nextObject = sel_registerName("nextObject");
public static final long /*int*/ sel_nextState = sel_registerName("nextState");
public static final long /*int*/ sel_nextWordFromIndex_forward_ = sel_registerName("nextWordFromIndex:forward:");
public static final long /*int*/ sel_noResponderFor_ = sel_registerName("noResponderFor:");
public static final long /*int*/ sel_normalizedPosition = sel_registerName("normalizedPosition");
public static final long /*int*/ sel_noteNumberOfRowsChanged = sel_registerName("noteNumberOfRowsChanged");
public static final long /*int*/ sel_numberOfColumns = sel_registerName("numberOfColumns");
public static final long /*int*/ sel_numberOfComponents = sel_registerName("numberOfComponents");
public static final long /*int*/ sel_numberOfGlyphs = sel_registerName("numberOfGlyphs");
public static final long /*int*/ sel_numberOfItems = sel_registerName("numberOfItems");
public static final long /*int*/ sel_numberOfPlanes = sel_registerName("numberOfPlanes");
public static final long /*int*/ sel_numberOfRows = sel_registerName("numberOfRows");
public static final long /*int*/ sel_numberOfRowsInTableView_ = sel_registerName("numberOfRowsInTableView:");
public static final long /*int*/ sel_numberOfSelectedRows = sel_registerName("numberOfSelectedRows");
public static final long /*int*/ sel_numberOfVisibleItems = sel_registerName("numberOfVisibleItems");
public static final long /*int*/ sel_numberWithBool_ = sel_registerName("numberWithBool:");
public static final long /*int*/ sel_numberWithDouble_ = sel_registerName("numberWithDouble:");
public static final long /*int*/ sel_numberWithInt_ = sel_registerName("numberWithInt:");
public static final long /*int*/ sel_numberWithInteger_ = sel_registerName("numberWithInteger:");
public static final long /*int*/ sel_objCType = sel_registerName("objCType");
public static final long /*int*/ sel_object = sel_registerName("object");
public static final long /*int*/ sel_objectAtIndex_ = sel_registerName("objectAtIndex:");
public static final long /*int*/ sel_objectEnumerator = sel_registerName("objectEnumerator");
public static final long /*int*/ sel_objectForInfoDictionaryKey_ = sel_registerName("objectForInfoDictionaryKey:");
public static final long /*int*/ sel_objectForKey_ = sel_registerName("objectForKey:");
public static final long /*int*/ sel_objectValues = sel_registerName("objectValues");
public static final long /*int*/ sel_openFile_withApplication_ = sel_registerName("openFile:withApplication:");
public static final long /*int*/ sel_openPanel = sel_registerName("openPanel");
public static final long /*int*/ sel_openURL_ = sel_registerName("openURL:");
public static final long /*int*/ sel_openURLs_withAppBundleIdentifier_options_additionalEventParamDescriptor_launchIdentifiers_ = sel_registerName("openURLs:withAppBundleIdentifier:options:additionalEventParamDescriptor:launchIdentifiers:");
public static final long /*int*/ sel_options = sel_registerName("options");
public static final long /*int*/ sel_orderBack_ = sel_registerName("orderBack:");
public static final long /*int*/ sel_orderFront_ = sel_registerName("orderFront:");
public static final long /*int*/ sel_orderFrontRegardless = sel_registerName("orderFrontRegardless");
public static final long /*int*/ sel_orderFrontStandardAboutPanel_ = sel_registerName("orderFrontStandardAboutPanel:");
public static final long /*int*/ sel_orderOut_ = sel_registerName("orderOut:");
public static final long /*int*/ sel_orderWindow_relativeTo_ = sel_registerName("orderWindow:relativeTo:");
public static final long /*int*/ sel_orderedWindows = sel_registerName("orderedWindows");
public static final long /*int*/ sel_otherEventWithType_location_modifierFlags_timestamp_windowNumber_context_subtype_data1_data2_ = sel_registerName("otherEventWithType:location:modifierFlags:timestamp:windowNumber:context:subtype:data1:data2:");
public static final long /*int*/ sel_otherMouseDown_ = sel_registerName("otherMouseDown:");
public static final long /*int*/ sel_otherMouseDragged_ = sel_registerName("otherMouseDragged:");
public static final long /*int*/ sel_otherMouseUp_ = sel_registerName("otherMouseUp:");
public static final long /*int*/ sel_outlineTableColumn = sel_registerName("outlineTableColumn");
public static final long /*int*/ sel_outlineView_acceptDrop_item_childIndex_ = sel_registerName("outlineView:acceptDrop:item:childIndex:");
public static final long /*int*/ sel_outlineView_child_ofItem_ = sel_registerName("outlineView:child:ofItem:");
public static final long /*int*/ sel_outlineView_didClickTableColumn_ = sel_registerName("outlineView:didClickTableColumn:");
public static final long /*int*/ sel_outlineView_isItemExpandable_ = sel_registerName("outlineView:isItemExpandable:");
public static final long /*int*/ sel_outlineView_numberOfChildrenOfItem_ = sel_registerName("outlineView:numberOfChildrenOfItem:");
public static final long /*int*/ sel_outlineView_objectValueForTableColumn_byItem_ = sel_registerName("outlineView:objectValueForTableColumn:byItem:");
public static final long /*int*/ sel_outlineView_setObjectValue_forTableColumn_byItem_ = sel_registerName("outlineView:setObjectValue:forTableColumn:byItem:");
public static final long /*int*/ sel_outlineView_shouldCollapseItem_ = sel_registerName("outlineView:shouldCollapseItem:");
public static final long /*int*/ sel_outlineView_shouldEditTableColumn_item_ = sel_registerName("outlineView:shouldEditTableColumn:item:");
public static final long /*int*/ sel_outlineView_shouldExpandItem_ = sel_registerName("outlineView:shouldExpandItem:");
public static final long /*int*/ sel_outlineView_shouldReorderColumn_toColumn_ = sel_registerName("outlineView:shouldReorderColumn:toColumn:");
public static final long /*int*/ sel_outlineView_shouldSelectItem_ = sel_registerName("outlineView:shouldSelectItem:");
public static final long /*int*/ sel_outlineView_shouldTrackCell_forTableColumn_item_ = sel_registerName("outlineView:shouldTrackCell:forTableColumn:item:");
public static final long /*int*/ sel_outlineView_validateDrop_proposedItem_proposedChildIndex_ = sel_registerName("outlineView:validateDrop:proposedItem:proposedChildIndex:");
public static final long /*int*/ sel_outlineView_willDisplayCell_forTableColumn_item_ = sel_registerName("outlineView:willDisplayCell:forTableColumn:item:");
public static final long /*int*/ sel_outlineView_writeItems_toPasteboard_ = sel_registerName("outlineView:writeItems:toPasteboard:");
public static final long /*int*/ sel_outlineViewColumnDidMove_ = sel_registerName("outlineViewColumnDidMove:");
public static final long /*int*/ sel_outlineViewColumnDidResize_ = sel_registerName("outlineViewColumnDidResize:");
public static final long /*int*/ sel_outlineViewItemDidExpand_ = sel_registerName("outlineViewItemDidExpand:");
public static final long /*int*/ sel_outlineViewSelectionDidChange_ = sel_registerName("outlineViewSelectionDidChange:");
public static final long /*int*/ sel_outlineViewSelectionIsChanging_ = sel_registerName("outlineViewSelectionIsChanging:");
public static final long /*int*/ sel_owner = sel_registerName("owner");
public static final long /*int*/ sel_pageDown_ = sel_registerName("pageDown:");
public static final long /*int*/ sel_pageTitle = sel_registerName("pageTitle");
public static final long /*int*/ sel_pageUp_ = sel_registerName("pageUp:");
public static final long /*int*/ sel_panel_shouldShowFilename_ = sel_registerName("panel:shouldShowFilename:");
public static final long /*int*/ sel_panelConvertFont_ = sel_registerName("panelConvertFont:");
public static final long /*int*/ sel_paperSize = sel_registerName("paperSize");
public static final long /*int*/ sel_paragraphs = sel_registerName("paragraphs");
public static final long /*int*/ sel_parentWindow = sel_registerName("parentWindow");
public static final long /*int*/ sel_password = sel_registerName("password");
public static final long /*int*/ sel_paste_ = sel_registerName("paste:");
public static final long /*int*/ sel_pasteboard_provideDataForType_ = sel_registerName("pasteboard:provideDataForType:");
public static final long /*int*/ sel_pasteboardWithName_ = sel_registerName("pasteboardWithName:");
public static final long /*int*/ sel_path = sel_registerName("path");
public static final long /*int*/ sel_pathExtension = sel_registerName("pathExtension");
public static final long /*int*/ sel_pathForResource_ofType_ = sel_registerName("pathForResource:ofType:");
public static final long /*int*/ sel_pathForResource_ofType_inDirectory_forLocalization_ = sel_registerName("pathForResource:ofType:inDirectory:forLocalization:");
public static final long /*int*/ sel_performDragOperation_ = sel_registerName("performDragOperation:");
public static final long /*int*/ sel_performKeyEquivalent_ = sel_registerName("performKeyEquivalent:");
public static final long /*int*/ sel_performSelector_withObject_afterDelay_inModes_ = sel_registerName("performSelector:withObject:afterDelay:inModes:");
public static final long /*int*/ sel_performSelectorOnMainThread_withObject_waitUntilDone_ = sel_registerName("performSelectorOnMainThread:withObject:waitUntilDone:");
public static final long /*int*/ sel_phase = sel_registerName("phase");
public static final long /*int*/ sel_pixelsHigh = sel_registerName("pixelsHigh");
public static final long /*int*/ sel_pixelsWide = sel_registerName("pixelsWide");
public static final long /*int*/ sel_pointSize = sel_registerName("pointSize");
public static final long /*int*/ sel_pointValue = sel_registerName("pointValue");
public static final long /*int*/ sel_pointingHandCursor = sel_registerName("pointingHandCursor");
public static final long /*int*/ sel_pop = sel_registerName("pop");
public static final long /*int*/ sel_popUpContextMenu_withEvent_forView_ = sel_registerName("popUpContextMenu:withEvent:forView:");
public static final long /*int*/ sel_popUpStatusItemMenu_ = sel_registerName("popUpStatusItemMenu:");
public static final long /*int*/ sel_port = sel_registerName("port");
public static final long /*int*/ sel_postEvent_atStart_ = sel_registerName("postEvent:atStart:");
public static final long /*int*/ sel_preparedCellAtColumn_row_ = sel_registerName("preparedCellAtColumn:row:");
public static final long /*int*/ sel_prependTransform_ = sel_registerName("prependTransform:");
public static final long /*int*/ sel_preventDefault = sel_registerName("preventDefault");
public static final long /*int*/ sel_previousFailureCount = sel_registerName("previousFailureCount");
public static final long /*int*/ sel_printDocumentView = sel_registerName("printDocumentView");
public static final long /*int*/ sel_printOperationWithPrintInfo_ = sel_registerName("printOperationWithPrintInfo:");
public static final long /*int*/ sel_printOperationWithView_printInfo_ = sel_registerName("printOperationWithView:printInfo:");
public static final long /*int*/ sel_printPanel = sel_registerName("printPanel");
public static final long /*int*/ sel_printSettings = sel_registerName("printSettings");
public static final long /*int*/ sel_printer = sel_registerName("printer");
public static final long /*int*/ sel_printerNames = sel_registerName("printerNames");
public static final long /*int*/ sel_printerWithName_ = sel_registerName("printerWithName:");
public static final long /*int*/ sel_propertyListForType_ = sel_registerName("propertyListForType:");
public static final long /*int*/ sel_proposedCredential = sel_registerName("proposedCredential");
public static final long /*int*/ sel_protectionSpace = sel_registerName("protectionSpace");
public static final long /*int*/ sel_push = sel_registerName("push");
public static final long /*int*/ sel_rangeValue = sel_registerName("rangeValue");
public static final long /*int*/ sel_readSelectionFromPasteboard_ = sel_registerName("readSelectionFromPasteboard:");
public static final long /*int*/ sel_realm = sel_registerName("realm");
public static final long /*int*/ sel_recentSearches = sel_registerName("recentSearches");
public static final long /*int*/ sel_rectArrayForCharacterRange_withinSelectedCharacterRange_inTextContainer_rectCount_ = sel_registerName("rectArrayForCharacterRange:withinSelectedCharacterRange:inTextContainer:rectCount:");
public static final long /*int*/ sel_rectArrayForGlyphRange_withinSelectedGlyphRange_inTextContainer_rectCount_ = sel_registerName("rectArrayForGlyphRange:withinSelectedGlyphRange:inTextContainer:rectCount:");
public static final long /*int*/ sel_rectForPart_ = sel_registerName("rectForPart:");
public static final long /*int*/ sel_rectOfColumn_ = sel_registerName("rectOfColumn:");
public static final long /*int*/ sel_rectOfRow_ = sel_registerName("rectOfRow:");
public static final long /*int*/ sel_rectValue = sel_registerName("rectValue");
public static final long /*int*/ sel_redComponent = sel_registerName("redComponent");
public static final long /*int*/ sel_redo = sel_registerName("redo");
public static final long /*int*/ sel_reflectScrolledClipView_ = sel_registerName("reflectScrolledClipView:");
public static final long /*int*/ sel_registerForDraggedTypes_ = sel_registerName("registerForDraggedTypes:");
public static final long /*int*/ sel_release = sel_registerName("release");
public static final long /*int*/ sel_reload_ = sel_registerName("reload:");
public static final long /*int*/ sel_reloadData = sel_registerName("reloadData");
public static final long /*int*/ sel_reloadItem_reloadChildren_ = sel_registerName("reloadItem:reloadChildren:");
public static final long /*int*/ sel_removeAllItems = sel_registerName("removeAllItems");
public static final long /*int*/ sel_removeAllPoints = sel_registerName("removeAllPoints");
public static final long /*int*/ sel_removeAttribute_range_ = sel_registerName("removeAttribute:range:");
public static final long /*int*/ sel_removeChildWindow_ = sel_registerName("removeChildWindow:");
public static final long /*int*/ sel_removeColorWithKey_ = sel_registerName("removeColorWithKey:");
public static final long /*int*/ sel_removeFromSuperview = sel_registerName("removeFromSuperview");
public static final long /*int*/ sel_removeIndex_ = sel_registerName("removeIndex:");
public static final long /*int*/ sel_removeItem_ = sel_registerName("removeItem:");
public static final long /*int*/ sel_removeItemAtIndex_ = sel_registerName("removeItemAtIndex:");
public static final long /*int*/ sel_removeItemAtPath_error_ = sel_registerName("removeItemAtPath:error:");
public static final long /*int*/ sel_removeLastObject = sel_registerName("removeLastObject");
public static final long /*int*/ sel_removeObject_ = sel_registerName("removeObject:");
public static final long /*int*/ sel_removeObjectAtIndex_ = sel_registerName("removeObjectAtIndex:");
public static final long /*int*/ sel_removeObjectForKey_ = sel_registerName("removeObjectForKey:");
public static final long /*int*/ sel_removeObjectIdenticalTo_ = sel_registerName("removeObjectIdenticalTo:");
public static final long /*int*/ sel_removeObserver_ = sel_registerName("removeObserver:");
public static final long /*int*/ sel_removeObserver_name_object_ = sel_registerName("removeObserver:name:object:");
public static final long /*int*/ sel_removeRepresentation_ = sel_registerName("removeRepresentation:");
public static final long /*int*/ sel_removeStatusItem_ = sel_registerName("removeStatusItem:");
public static final long /*int*/ sel_removeTabViewItem_ = sel_registerName("removeTabViewItem:");
public static final long /*int*/ sel_removeTableColumn_ = sel_registerName("removeTableColumn:");
public static final long /*int*/ sel_removeTemporaryAttribute_forCharacterRange_ = sel_registerName("removeTemporaryAttribute:forCharacterRange:");
public static final long /*int*/ sel_removeToolTip_ = sel_registerName("removeToolTip:");
public static final long /*int*/ sel_removeTrackingArea_ = sel_registerName("removeTrackingArea:");
public static final long /*int*/ sel_replaceCharactersInRange_withString_ = sel_registerName("replaceCharactersInRange:withString:");
public static final long /*int*/ sel_replyToOpenOrPrint_ = sel_registerName("replyToOpenOrPrint:");
public static final long /*int*/ sel_representation = sel_registerName("representation");
public static final long /*int*/ sel_representations = sel_registerName("representations");
public static final long /*int*/ sel_request = sel_registerName("request");
public static final long /*int*/ sel_requestWithURL_ = sel_registerName("requestWithURL:");
public static final long /*int*/ sel_resetCursorRects = sel_registerName("resetCursorRects");
public static final long /*int*/ sel_resignFirstResponder = sel_registerName("resignFirstResponder");
public static final long /*int*/ sel_resizeDownCursor = sel_registerName("resizeDownCursor");
public static final long /*int*/ sel_resizeLeftCursor = sel_registerName("resizeLeftCursor");
public static final long /*int*/ sel_resizeLeftRightCursor = sel_registerName("resizeLeftRightCursor");
public static final long /*int*/ sel_resizeRightCursor = sel_registerName("resizeRightCursor");
public static final long /*int*/ sel_resizeUpCursor = sel_registerName("resizeUpCursor");
public static final long /*int*/ sel_resizeUpDownCursor = sel_registerName("resizeUpDownCursor");
public static final long /*int*/ sel_resizingMask = sel_registerName("resizingMask");
public static final long /*int*/ sel_resourcePath = sel_registerName("resourcePath");
public static final long /*int*/ sel_respondsToSelector_ = sel_registerName("respondsToSelector:");
public static final long /*int*/ sel_restoreGraphicsState = sel_registerName("restoreGraphicsState");
public static final long /*int*/ sel_retain = sel_registerName("retain");
public static final long /*int*/ sel_retainCount = sel_registerName("retainCount");
public static final long /*int*/ sel_rightMouseDown_ = sel_registerName("rightMouseDown:");
public static final long /*int*/ sel_rightMouseDragged_ = sel_registerName("rightMouseDragged:");
public static final long /*int*/ sel_rightMouseUp_ = sel_registerName("rightMouseUp:");
public static final long /*int*/ sel_rotateByDegrees_ = sel_registerName("rotateByDegrees:");
public static final long /*int*/ sel_rotateWithEvent_ = sel_registerName("rotateWithEvent:");
public static final long /*int*/ sel_rotation = sel_registerName("rotation");
public static final long /*int*/ sel_rowAtPoint_ = sel_registerName("rowAtPoint:");
public static final long /*int*/ sel_rowForItem_ = sel_registerName("rowForItem:");
public static final long /*int*/ sel_rowHeight = sel_registerName("rowHeight");
public static final long /*int*/ sel_rowsInRect_ = sel_registerName("rowsInRect:");
public static final long /*int*/ sel_run = sel_registerName("run");
public static final long /*int*/ sel_runModal = sel_registerName("runModal");
public static final long /*int*/ sel_runModalForDirectory_file_ = sel_registerName("runModalForDirectory:file:");
public static final long /*int*/ sel_runModalForWindow_ = sel_registerName("runModalForWindow:");
public static final long /*int*/ sel_runModalWithPrintInfo_ = sel_registerName("runModalWithPrintInfo:");
public static final long /*int*/ sel_runMode_beforeDate_ = sel_registerName("runMode:beforeDate:");
public static final long /*int*/ sel_runOperation = sel_registerName("runOperation");
public static final long /*int*/ sel_samplesPerPixel = sel_registerName("samplesPerPixel");
public static final long /*int*/ sel_saveGraphicsState = sel_registerName("saveGraphicsState");
public static final long /*int*/ sel_savePanel = sel_registerName("savePanel");
public static final long /*int*/ sel_scaleXBy_yBy_ = sel_registerName("scaleXBy:yBy:");
public static final long /*int*/ sel_scheduledTimerWithTimeInterval_target_selector_userInfo_repeats_ = sel_registerName("scheduledTimerWithTimeInterval:target:selector:userInfo:repeats:");
public static final long /*int*/ sel_screen = sel_registerName("screen");
public static final long /*int*/ sel_screenX = sel_registerName("screenX");
public static final long /*int*/ sel_screenY = sel_registerName("screenY");
public static final long /*int*/ sel_screens = sel_registerName("screens");
public static final long /*int*/ sel_scrollClipView_toPoint_ = sel_registerName("scrollClipView:toPoint:");
public static final long /*int*/ sel_scrollColumnToVisible_ = sel_registerName("scrollColumnToVisible:");
public static final long /*int*/ sel_scrollPoint_ = sel_registerName("scrollPoint:");
public static final long /*int*/ sel_scrollRangeToVisible_ = sel_registerName("scrollRangeToVisible:");
public static final long /*int*/ sel_scrollRectToVisible_ = sel_registerName("scrollRectToVisible:");
public static final long /*int*/ sel_scrollRowToVisible_ = sel_registerName("scrollRowToVisible:");
public static final long /*int*/ sel_scrollToPoint_ = sel_registerName("scrollToPoint:");
public static final long /*int*/ sel_scrollWheel_ = sel_registerName("scrollWheel:");
public static final long /*int*/ sel_scrollerWidth = sel_registerName("scrollerWidth");
public static final long /*int*/ sel_scrollerWidthForControlSize_ = sel_registerName("scrollerWidthForControlSize:");
public static final long /*int*/ sel_searchButtonCell = sel_registerName("searchButtonCell");
public static final long /*int*/ sel_searchTextRectForBounds_ = sel_registerName("searchTextRectForBounds:");
public static final long /*int*/ sel_secondOfMinute = sel_registerName("secondOfMinute");
public static final long /*int*/ sel_secondarySelectedControlColor = sel_registerName("secondarySelectedControlColor");
public static final long /*int*/ sel_selectAll_ = sel_registerName("selectAll:");
public static final long /*int*/ sel_selectItem_ = sel_registerName("selectItem:");
public static final long /*int*/ sel_selectItemAtIndex_ = sel_registerName("selectItemAtIndex:");
public static final long /*int*/ sel_selectRowIndexes_byExtendingSelection_ = sel_registerName("selectRowIndexes:byExtendingSelection:");
public static final long /*int*/ sel_selectTabViewItemAtIndex_ = sel_registerName("selectTabViewItemAtIndex:");
public static final long /*int*/ sel_selectText_ = sel_registerName("selectText:");
public static final long /*int*/ sel_selectedControlColor = sel_registerName("selectedControlColor");
public static final long /*int*/ sel_selectedControlTextColor = sel_registerName("selectedControlTextColor");
public static final long /*int*/ sel_selectedRange = sel_registerName("selectedRange");
public static final long /*int*/ sel_selectedRow = sel_registerName("selectedRow");
public static final long /*int*/ sel_selectedRowIndexes = sel_registerName("selectedRowIndexes");
public static final long /*int*/ sel_selectedTabViewItem = sel_registerName("selectedTabViewItem");
public static final long /*int*/ sel_selectedTextAttributes = sel_registerName("selectedTextAttributes");
public static final long /*int*/ sel_selectedTextBackgroundColor = sel_registerName("selectedTextBackgroundColor");
public static final long /*int*/ sel_selectedTextColor = sel_registerName("selectedTextColor");
public static final long /*int*/ sel_sendAction_to_ = sel_registerName("sendAction:to:");
public static final long /*int*/ sel_sendAction_to_from_ = sel_registerName("sendAction:to:from:");
public static final long /*int*/ sel_sendEvent_ = sel_registerName("sendEvent:");
public static final long /*int*/ sel_sender = sel_registerName("sender");
public static final long /*int*/ sel_separatorItem = sel_registerName("separatorItem");
public static final long /*int*/ sel_set = sel_registerName("set");
public static final long /*int*/ sel_setAcceptsMouseMovedEvents_ = sel_registerName("setAcceptsMouseMovedEvents:");
public static final long /*int*/ sel_setAcceptsTouchEvents_ = sel_registerName("setAcceptsTouchEvents:");
public static final long /*int*/ sel_setAccessoryView_ = sel_registerName("setAccessoryView:");
public static final long /*int*/ sel_setAction_ = sel_registerName("setAction:");
public static final long /*int*/ sel_setAlertStyle_ = sel_registerName("setAlertStyle:");
public static final long /*int*/ sel_setAlignment_ = sel_registerName("setAlignment:");
public static final long /*int*/ sel_setAllowsColumnReordering_ = sel_registerName("setAllowsColumnReordering:");
public static final long /*int*/ sel_setAllowsFloats_ = sel_registerName("setAllowsFloats:");
public static final long /*int*/ sel_setAllowsMixedState_ = sel_registerName("setAllowsMixedState:");
public static final long /*int*/ sel_setAllowsMultipleSelection_ = sel_registerName("setAllowsMultipleSelection:");
public static final long /*int*/ sel_setAllowsUndo_ = sel_registerName("setAllowsUndo:");
public static final long /*int*/ sel_setAllowsUserCustomization_ = sel_registerName("setAllowsUserCustomization:");
public static final long /*int*/ sel_setAlpha_ = sel_registerName("setAlpha:");
public static final long /*int*/ sel_setAlphaValue_ = sel_registerName("setAlphaValue:");
public static final long /*int*/ sel_setAlternateButtonTitle_ = sel_registerName("setAlternateButtonTitle:");
public static final long /*int*/ sel_setApplicationIconImage_ = sel_registerName("setApplicationIconImage:");
public static final long /*int*/ sel_setApplicationNameForUserAgent_ = sel_registerName("setApplicationNameForUserAgent:");
public static final long /*int*/ sel_setAttachmentCell_ = sel_registerName("setAttachmentCell:");
public static final long /*int*/ sel_setAttributedString_ = sel_registerName("setAttributedString:");
public static final long /*int*/ sel_setAttributedStringValue_ = sel_registerName("setAttributedStringValue:");
public static final long /*int*/ sel_setAttributedTitle_ = sel_registerName("setAttributedTitle:");
public static final long /*int*/ sel_setAutoenablesItems_ = sel_registerName("setAutoenablesItems:");
public static final long /*int*/ sel_setAutohidesScrollers_ = sel_registerName("setAutohidesScrollers:");
public static final long /*int*/ sel_setAutoresizesOutlineColumn_ = sel_registerName("setAutoresizesOutlineColumn:");
public static final long /*int*/ sel_setAutoresizesSubviews_ = sel_registerName("setAutoresizesSubviews:");
public static final long /*int*/ sel_setAutoresizingMask_ = sel_registerName("setAutoresizingMask:");
public static final long /*int*/ sel_setAutosaveExpandedItems_ = sel_registerName("setAutosaveExpandedItems:");
public static final long /*int*/ sel_setBackgroundColor_ = sel_registerName("setBackgroundColor:");
public static final long /*int*/ sel_setBackgroundLayoutEnabled_ = sel_registerName("setBackgroundLayoutEnabled:");
public static final long /*int*/ sel_setBackgroundStyle_ = sel_registerName("setBackgroundStyle:");
public static final long /*int*/ sel_setBadgeLabel_ = sel_registerName("setBadgeLabel:");
public static final long /*int*/ sel_setBaseWritingDirection_ = sel_registerName("setBaseWritingDirection:");
public static final long /*int*/ sel_setBaseWritingDirection_range_ = sel_registerName("setBaseWritingDirection:range:");
public static final long /*int*/ sel_setBecomesKeyOnlyIfNeeded_ = sel_registerName("setBecomesKeyOnlyIfNeeded:");
public static final long /*int*/ sel_setBezelStyle_ = sel_registerName("setBezelStyle:");
public static final long /*int*/ sel_setBezeled_ = sel_registerName("setBezeled:");
public static final long /*int*/ sel_setBorderType_ = sel_registerName("setBorderType:");
public static final long /*int*/ sel_setBorderWidth_ = sel_registerName("setBorderWidth:");
public static final long /*int*/ sel_setBordered_ = sel_registerName("setBordered:");
public static final long /*int*/ sel_setBoundsRotation_ = sel_registerName("setBoundsRotation:");
public static final long /*int*/ sel_setBoxType_ = sel_registerName("setBoxType:");
public static final long /*int*/ sel_setButtonType_ = sel_registerName("setButtonType:");
public static final long /*int*/ sel_setCacheMode_ = sel_registerName("setCacheMode:");
public static final long /*int*/ sel_setCachePolicy_ = sel_registerName("setCachePolicy:");
public static final long /*int*/ sel_setCanChooseDirectories_ = sel_registerName("setCanChooseDirectories:");
public static final long /*int*/ sel_setCanChooseFiles_ = sel_registerName("setCanChooseFiles:");
public static final long /*int*/ sel_setCanCreateDirectories_ = sel_registerName("setCanCreateDirectories:");
public static final long /*int*/ sel_setCancelButtonCell_ = sel_registerName("setCancelButtonCell:");
public static final long /*int*/ sel_setCell_ = sel_registerName("setCell:");
public static final long /*int*/ sel_setCellClass_ = sel_registerName("setCellClass:");
public static final long /*int*/ sel_setClip = sel_registerName("setClip");
public static final long /*int*/ sel_setCollectionBehavior_ = sel_registerName("setCollectionBehavior:");
public static final long /*int*/ sel_setColor_ = sel_registerName("setColor:");
public static final long /*int*/ sel_setColumnAutoresizingStyle_ = sel_registerName("setColumnAutoresizingStyle:");
public static final long /*int*/ sel_setCompositingOperation_ = sel_registerName("setCompositingOperation:");
public static final long /*int*/ sel_setContainerSize_ = sel_registerName("setContainerSize:");
public static final long /*int*/ sel_setContentView_ = sel_registerName("setContentView:");
public static final long /*int*/ sel_setContentViewMargins_ = sel_registerName("setContentViewMargins:");
public static final long /*int*/ sel_setControlSize_ = sel_registerName("setControlSize:");
public static final long /*int*/ sel_setCookie_ = sel_registerName("setCookie:");
public static final long /*int*/ sel_setCopiesOnScroll_ = sel_registerName("setCopiesOnScroll:");
public static final long /*int*/ sel_setCurrentContext_ = sel_registerName("setCurrentContext:");
public static final long /*int*/ sel_setCurrentOperation_ = sel_registerName("setCurrentOperation:");
public static final long /*int*/ sel_setCustomUserAgent_ = sel_registerName("setCustomUserAgent:");
public static final long /*int*/ sel_setData_forType_ = sel_registerName("setData:forType:");
public static final long /*int*/ sel_setDataCell_ = sel_registerName("setDataCell:");
public static final long /*int*/ sel_setDataSource_ = sel_registerName("setDataSource:");
public static final long /*int*/ sel_setDatePickerElements_ = sel_registerName("setDatePickerElements:");
public static final long /*int*/ sel_setDatePickerStyle_ = sel_registerName("setDatePickerStyle:");
public static final long /*int*/ sel_setDateValue_ = sel_registerName("setDateValue:");
public static final long /*int*/ sel_setDefaultButtonCell_ = sel_registerName("setDefaultButtonCell:");
public static final long /*int*/ sel_setDefaultFlatness_ = sel_registerName("setDefaultFlatness:");
public static final long /*int*/ sel_setDefaultParagraphStyle_ = sel_registerName("setDefaultParagraphStyle:");
public static final long /*int*/ sel_setDefaultTabInterval_ = sel_registerName("setDefaultTabInterval:");
public static final long /*int*/ sel_setDelegate_ = sel_registerName("setDelegate:");
public static final long /*int*/ sel_setDestination_allowOverwrite_ = sel_registerName("setDestination:allowOverwrite:");
public static final long /*int*/ sel_setDictionary_ = sel_registerName("setDictionary:");
public static final long /*int*/ sel_setDirectory_ = sel_registerName("setDirectory:");
public static final long /*int*/ sel_setDisplayMode_ = sel_registerName("setDisplayMode:");
public static final long /*int*/ sel_setDisplaysLinkToolTips_ = sel_registerName("setDisplaysLinkToolTips:");
public static final long /*int*/ sel_setDocumentCursor_ = sel_registerName("setDocumentCursor:");
public static final long /*int*/ sel_setDocumentEdited_ = sel_registerName("setDocumentEdited:");
public static final long /*int*/ sel_setDocumentView_ = sel_registerName("setDocumentView:");
public static final long /*int*/ sel_setDoubleAction_ = sel_registerName("setDoubleAction:");
public static final long /*int*/ sel_setDoubleValue_ = sel_registerName("setDoubleValue:");
public static final long /*int*/ sel_setDownloadDelegate_ = sel_registerName("setDownloadDelegate:");
public static final long /*int*/ sel_setDrawsBackground_ = sel_registerName("setDrawsBackground:");
public static final long /*int*/ sel_setDropItem_dropChildIndex_ = sel_registerName("setDropItem:dropChildIndex:");
public static final long /*int*/ sel_setDropRow_dropOperation_ = sel_registerName("setDropRow:dropOperation:");
public static final long /*int*/ sel_setEditable_ = sel_registerName("setEditable:");
public static final long /*int*/ sel_setEnabled_ = sel_registerName("setEnabled:");
public static final long /*int*/ sel_setEnabled_forSegment_ = sel_registerName("setEnabled:forSegment:");
public static final long /*int*/ sel_setFill = sel_registerName("setFill");
public static final long /*int*/ sel_setFillColor_ = sel_registerName("setFillColor:");
public static final long /*int*/ sel_setFireDate_ = sel_registerName("setFireDate:");
public static final long /*int*/ sel_setFirstLineHeadIndent_ = sel_registerName("setFirstLineHeadIndent:");
public static final long /*int*/ sel_setFloatingPanel_ = sel_registerName("setFloatingPanel:");
public static final long /*int*/ sel_setFocusRingType_ = sel_registerName("setFocusRingType:");
public static final long /*int*/ sel_setFont_ = sel_registerName("setFont:");
public static final long /*int*/ sel_setFormatter_ = sel_registerName("setFormatter:");
public static final long /*int*/ sel_setFrame_ = sel_registerName("setFrame:");
public static final long /*int*/ sel_setFrame_display_ = sel_registerName("setFrame:display:");
public static final long /*int*/ sel_setFrame_display_animate_ = sel_registerName("setFrame:display:animate:");
public static final long /*int*/ sel_setFrameFromContentFrame_ = sel_registerName("setFrameFromContentFrame:");
public static final long /*int*/ sel_setFrameLoadDelegate_ = sel_registerName("setFrameLoadDelegate:");
public static final long /*int*/ sel_setFrameOrigin_ = sel_registerName("setFrameOrigin:");
public static final long /*int*/ sel_setFrameSize_ = sel_registerName("setFrameSize:");
public static final long /*int*/ sel_setGridStyleMask_ = sel_registerName("setGridStyleMask:");
public static final long /*int*/ sel_setHTTPBody_ = sel_registerName("setHTTPBody:");
public static final long /*int*/ sel_setHTTPMethod_ = sel_registerName("setHTTPMethod:");
public static final long /*int*/ sel_setHasHorizontalScroller_ = sel_registerName("setHasHorizontalScroller:");
public static final long /*int*/ sel_setHasShadow_ = sel_registerName("setHasShadow:");
public static final long /*int*/ sel_setHasVerticalScroller_ = sel_registerName("setHasVerticalScroller:");
public static final long /*int*/ sel_setHeadIndent_ = sel_registerName("setHeadIndent:");
public static final long /*int*/ sel_setHeaderCell_ = sel_registerName("setHeaderCell:");
public static final long /*int*/ sel_setHeaderView_ = sel_registerName("setHeaderView:");
public static final long /*int*/ sel_setHelpMenu_ = sel_registerName("setHelpMenu:");
public static final long /*int*/ sel_setHidden_ = sel_registerName("setHidden:");
public static final long /*int*/ sel_setHiddenUntilMouseMoves_ = sel_registerName("setHiddenUntilMouseMoves:");
public static final long /*int*/ sel_setHidesOnDeactivate_ = sel_registerName("setHidesOnDeactivate:");
public static final long /*int*/ sel_setHighlightMode_ = sel_registerName("setHighlightMode:");
public static final long /*int*/ sel_setHighlighted_ = sel_registerName("setHighlighted:");
public static final long /*int*/ sel_setHighlightedTableColumn_ = sel_registerName("setHighlightedTableColumn:");
public static final long /*int*/ sel_setHighlightsBy_ = sel_registerName("setHighlightsBy:");
public static final long /*int*/ sel_setHorizontalScroller_ = sel_registerName("setHorizontalScroller:");
public static final long /*int*/ sel_setHorizontallyResizable_ = sel_registerName("setHorizontallyResizable:");
public static final long /*int*/ sel_setIcon_ = sel_registerName("setIcon:");
public static final long /*int*/ sel_setIdentifier_ = sel_registerName("setIdentifier:");
public static final long /*int*/ sel_setImage_ = sel_registerName("setImage:");
public static final long /*int*/ sel_setImage_forSegment_ = sel_registerName("setImage:forSegment:");
public static final long /*int*/ sel_setImageAlignment_ = sel_registerName("setImageAlignment:");
public static final long /*int*/ sel_setImageInterpolation_ = sel_registerName("setImageInterpolation:");
public static final long /*int*/ sel_setImagePosition_ = sel_registerName("setImagePosition:");
public static final long /*int*/ sel_setImageScaling_ = sel_registerName("setImageScaling:");
public static final long /*int*/ sel_setIncrement_ = sel_registerName("setIncrement:");
public static final long /*int*/ sel_setIndeterminate_ = sel_registerName("setIndeterminate:");
public static final long /*int*/ sel_setIndicatorImage_inTableColumn_ = sel_registerName("setIndicatorImage:inTableColumn:");
public static final long /*int*/ sel_setInteger_forKey_ = sel_registerName("setInteger:forKey:");
public static final long /*int*/ sel_setIntercellSpacing_ = sel_registerName("setIntercellSpacing:");
public static final long /*int*/ sel_setJavaEnabled_ = sel_registerName("setJavaEnabled:");
public static final long /*int*/ sel_setJavaScriptEnabled_ = sel_registerName("setJavaScriptEnabled:");
public static final long /*int*/ sel_setJobDisposition_ = sel_registerName("setJobDisposition:");
public static final long /*int*/ sel_setJobTitle_ = sel_registerName("setJobTitle:");
public static final long /*int*/ sel_setKeyEquivalent_ = sel_registerName("setKeyEquivalent:");
public static final long /*int*/ sel_setKeyEquivalentModifierMask_ = sel_registerName("setKeyEquivalentModifierMask:");
public static final long /*int*/ sel_setKnobProportion_ = sel_registerName("setKnobProportion:");
public static final long /*int*/ sel_setLabel_ = sel_registerName("setLabel:");
public static final long /*int*/ sel_setLabel_forSegment_ = sel_registerName("setLabel:forSegment:");
public static final long /*int*/ sel_setLeaf_ = sel_registerName("setLeaf:");
public static final long /*int*/ sel_setLength_ = sel_registerName("setLength:");
public static final long /*int*/ sel_setLevel_ = sel_registerName("setLevel:");
public static final long /*int*/ sel_setLineBreakMode_ = sel_registerName("setLineBreakMode:");
public static final long /*int*/ sel_setLineCapStyle_ = sel_registerName("setLineCapStyle:");
public static final long /*int*/ sel_setLineDash_count_phase_ = sel_registerName("setLineDash:count:phase:");
public static final long /*int*/ sel_setLineFragmentPadding_ = sel_registerName("setLineFragmentPadding:");
public static final long /*int*/ sel_setLineFragmentRect_forGlyphRange_usedRect_ = sel_registerName("setLineFragmentRect:forGlyphRange:usedRect:");
public static final long /*int*/ sel_setLineJoinStyle_ = sel_registerName("setLineJoinStyle:");
public static final long /*int*/ sel_setLineSpacing_ = sel_registerName("setLineSpacing:");
public static final long /*int*/ sel_setLineWidth_ = sel_registerName("setLineWidth:");
public static final long /*int*/ sel_setLinkTextAttributes_ = sel_registerName("setLinkTextAttributes:");
public static final long /*int*/ sel_setMainMenu_ = sel_registerName("setMainMenu:");
public static final long /*int*/ sel_setMarkedText_selectedRange_ = sel_registerName("setMarkedText:selectedRange:");
public static final long /*int*/ sel_setMaxSize_ = sel_registerName("setMaxSize:");
public static final long /*int*/ sel_setMaxValue_ = sel_registerName("setMaxValue:");
public static final long /*int*/ sel_setMaximum_ = sel_registerName("setMaximum:");
public static final long /*int*/ sel_setMaximumFractionDigits_ = sel_registerName("setMaximumFractionDigits:");
public static final long /*int*/ sel_setMaximumIntegerDigits_ = sel_registerName("setMaximumIntegerDigits:");
public static final long /*int*/ sel_setMenu_ = sel_registerName("setMenu:");
public static final long /*int*/ sel_setMenu_forSegment_ = sel_registerName("setMenu:forSegment:");
public static final long /*int*/ sel_setMenuFormRepresentation_ = sel_registerName("setMenuFormRepresentation:");
public static final long /*int*/ sel_setMessage_ = sel_registerName("setMessage:");
public static final long /*int*/ sel_setMessageText_ = sel_registerName("setMessageText:");
public static final long /*int*/ sel_setMinSize_ = sel_registerName("setMinSize:");
public static final long /*int*/ sel_setMinValue_ = sel_registerName("setMinValue:");
public static final long /*int*/ sel_setMinWidth_ = sel_registerName("setMinWidth:");
public static final long /*int*/ sel_setMinimum_ = sel_registerName("setMinimum:");
public static final long /*int*/ sel_setMinimumFractionDigits_ = sel_registerName("setMinimumFractionDigits:");
public static final long /*int*/ sel_setMinimumIntegerDigits_ = sel_registerName("setMinimumIntegerDigits:");
public static final long /*int*/ sel_setMiterLimit_ = sel_registerName("setMiterLimit:");
public static final long /*int*/ sel_setNeedsDisplay_ = sel_registerName("setNeedsDisplay:");
public static final long /*int*/ sel_setNeedsDisplayInRect_ = sel_registerName("setNeedsDisplayInRect:");
public static final long /*int*/ sel_setNumberOfVisibleItems_ = sel_registerName("setNumberOfVisibleItems:");
public static final long /*int*/ sel_setNumberStyle_ = sel_registerName("setNumberStyle:");
public static final long /*int*/ sel_setObject_forKey_ = sel_registerName("setObject:forKey:");
public static final long /*int*/ sel_setObjectValue_ = sel_registerName("setObjectValue:");
public static final long /*int*/ sel_setOnMouseEntered_ = sel_registerName("setOnMouseEntered:");
public static final long /*int*/ sel_setOpaque_ = sel_registerName("setOpaque:");
public static final long /*int*/ sel_setOptions_ = sel_registerName("setOptions:");
public static final long /*int*/ sel_setOutlineTableColumn_ = sel_registerName("setOutlineTableColumn:");
public static final long /*int*/ sel_setPaletteLabel_ = sel_registerName("setPaletteLabel:");
public static final long /*int*/ sel_setPanelFont_isMultiple_ = sel_registerName("setPanelFont:isMultiple:");
public static final long /*int*/ sel_setPartialStringValidationEnabled_ = sel_registerName("setPartialStringValidationEnabled:");
public static final long /*int*/ sel_setPatternPhase_ = sel_registerName("setPatternPhase:");
public static final long /*int*/ sel_setPlaceholderString_ = sel_registerName("setPlaceholderString:");
public static final long /*int*/ sel_setPolicyDelegate_ = sel_registerName("setPolicyDelegate:");
public static final long /*int*/ sel_setPreferences_ = sel_registerName("setPreferences:");
public static final long /*int*/ sel_setPrinter_ = sel_registerName("setPrinter:");
public static final long /*int*/ sel_setPropertyList_forType_ = sel_registerName("setPropertyList:forType:");
public static final long /*int*/ sel_setPullsDown_ = sel_registerName("setPullsDown:");
public static final long /*int*/ sel_setReleasedWhenClosed_ = sel_registerName("setReleasedWhenClosed:");
public static final long /*int*/ sel_setRepresentedFilename_ = sel_registerName("setRepresentedFilename:");
public static final long /*int*/ sel_setRepresentedURL_ = sel_registerName("setRepresentedURL:");
public static final long /*int*/ sel_setResizingMask_ = sel_registerName("setResizingMask:");
public static final long /*int*/ sel_setResourceLoadDelegate_ = sel_registerName("setResourceLoadDelegate:");
public static final long /*int*/ sel_setRichText_ = sel_registerName("setRichText:");
public static final long /*int*/ sel_setRowHeight_ = sel_registerName("setRowHeight:");
public static final long /*int*/ sel_setScalesWhenResized_ = sel_registerName("setScalesWhenResized:");
public static final long /*int*/ sel_setScrollable_ = sel_registerName("setScrollable:");
public static final long /*int*/ sel_setSearchButtonCell_ = sel_registerName("setSearchButtonCell:");
public static final long /*int*/ sel_setSegmentCount_ = sel_registerName("setSegmentCount:");
public static final long /*int*/ sel_setSegmentStyle_ = sel_registerName("setSegmentStyle:");
public static final long /*int*/ sel_setSelectable_ = sel_registerName("setSelectable:");
public static final long /*int*/ sel_setSelected_forSegment_ = sel_registerName("setSelected:forSegment:");
public static final long /*int*/ sel_setSelectedItemIdentifier_ = sel_registerName("setSelectedItemIdentifier:");
public static final long /*int*/ sel_setSelectedRange_ = sel_registerName("setSelectedRange:");
public static final long /*int*/ sel_setSelectedSegment_ = sel_registerName("setSelectedSegment:");
public static final long /*int*/ sel_setSelectedTextAttributes_ = sel_registerName("setSelectedTextAttributes:");
public static final long /*int*/ sel_setServicesMenu_ = sel_registerName("setServicesMenu:");
public static final long /*int*/ sel_setShouldAntialias_ = sel_registerName("setShouldAntialias:");
public static final long /*int*/ sel_setShowsHelp_ = sel_registerName("setShowsHelp:");
public static final long /*int*/ sel_setShowsPrintPanel_ = sel_registerName("setShowsPrintPanel:");
public static final long /*int*/ sel_setShowsProgressPanel_ = sel_registerName("setShowsProgressPanel:");
public static final long /*int*/ sel_setShowsResizeIndicator_ = sel_registerName("setShowsResizeIndicator:");
public static final long /*int*/ sel_setShowsToolbarButton_ = sel_registerName("setShowsToolbarButton:");
public static final long /*int*/ sel_setSize_ = sel_registerName("setSize:");
public static final long /*int*/ sel_setState_ = sel_registerName("setState:");
public static final long /*int*/ sel_setString_ = sel_registerName("setString:");
public static final long /*int*/ sel_setString_forType_ = sel_registerName("setString:forType:");
public static final long /*int*/ sel_setStringValue_ = sel_registerName("setStringValue:");
public static final long /*int*/ sel_setStroke = sel_registerName("setStroke");
public static final long /*int*/ sel_setSubmenu_ = sel_registerName("setSubmenu:");
public static final long /*int*/ sel_setSubmenu_forItem_ = sel_registerName("setSubmenu:forItem:");
public static final long /*int*/ sel_setTabStops_ = sel_registerName("setTabStops:");
public static final long /*int*/ sel_setTabViewType_ = sel_registerName("setTabViewType:");
public static final long /*int*/ sel_setTag_ = sel_registerName("setTag:");
public static final long /*int*/ sel_setTag_forSegment_ = sel_registerName("setTag:forSegment:");
public static final long /*int*/ sel_setTarget_ = sel_registerName("setTarget:");
public static final long /*int*/ sel_setTextColor_ = sel_registerName("setTextColor:");
public static final long /*int*/ sel_setTextStorage_ = sel_registerName("setTextStorage:");
public static final long /*int*/ sel_setTitle_ = sel_registerName("setTitle:");
public static final long /*int*/ sel_setTitleFont_ = sel_registerName("setTitleFont:");
public static final long /*int*/ sel_setTitlePosition_ = sel_registerName("setTitlePosition:");
public static final long /*int*/ sel_setToolTip_ = sel_registerName("setToolTip:");
public static final long /*int*/ sel_setToolTip_forSegment_ = sel_registerName("setToolTip:forSegment:");
public static final long /*int*/ sel_setToolbar_ = sel_registerName("setToolbar:");
public static final long /*int*/ sel_setTrackingMode_ = sel_registerName("setTrackingMode:");
public static final long /*int*/ sel_setTransformStruct_ = sel_registerName("setTransformStruct:");
public static final long /*int*/ sel_setUIDelegate_ = sel_registerName("setUIDelegate:");
public static final long /*int*/ sel_setURL_ = sel_registerName("setURL:");
public static final long /*int*/ sel_setUpPrintOperationDefaultValues = sel_registerName("setUpPrintOperationDefaultValues");
public static final long /*int*/ sel_setUsesAlternatingRowBackgroundColors_ = sel_registerName("setUsesAlternatingRowBackgroundColors:");
public static final long /*int*/ sel_setUsesFontPanel_ = sel_registerName("setUsesFontPanel:");
public static final long /*int*/ sel_setUsesScreenFonts_ = sel_registerName("setUsesScreenFonts:");
public static final long /*int*/ sel_setUsesSingleLineMode_ = sel_registerName("setUsesSingleLineMode:");
public static final long /*int*/ sel_setUsesThreadedAnimation_ = sel_registerName("setUsesThreadedAnimation:");
public static final long /*int*/ sel_setValue_forHTTPHeaderField_ = sel_registerName("setValue:forHTTPHeaderField:");
public static final long /*int*/ sel_setValue_forKey_ = sel_registerName("setValue:forKey:");
public static final long /*int*/ sel_setValueWraps_ = sel_registerName("setValueWraps:");
public static final long /*int*/ sel_setValues_forParameter_ = sel_registerName("setValues:forParameter:");
public static final long /*int*/ sel_setVerticalScroller_ = sel_registerName("setVerticalScroller:");
public static final long /*int*/ sel_setView_ = sel_registerName("setView:");
public static final long /*int*/ sel_setVisible_ = sel_registerName("setVisible:");
public static final long /*int*/ sel_setWantsRestingTouches_ = sel_registerName("setWantsRestingTouches:");
public static final long /*int*/ sel_setWidth_ = sel_registerName("setWidth:");
public static final long /*int*/ sel_setWidth_forSegment_ = sel_registerName("setWidth:forSegment:");
public static final long /*int*/ sel_setWidthTracksTextView_ = sel_registerName("setWidthTracksTextView:");
public static final long /*int*/ sel_setWindingRule_ = sel_registerName("setWindingRule:");
public static final long /*int*/ sel_setWorksWhenModal_ = sel_registerName("setWorksWhenModal:");
public static final long /*int*/ sel_setWraps_ = sel_registerName("setWraps:");
public static final long /*int*/ sel_sharedApplication = sel_registerName("sharedApplication");
public static final long /*int*/ sel_sharedCertificateTrustPanel = sel_registerName("sharedCertificateTrustPanel");
public static final long /*int*/ sel_sharedColorPanel = sel_registerName("sharedColorPanel");
public static final long /*int*/ sel_sharedFontManager = sel_registerName("sharedFontManager");
public static final long /*int*/ sel_sharedFontPanel = sel_registerName("sharedFontPanel");
public static final long /*int*/ sel_sharedHTTPCookieStorage = sel_registerName("sharedHTTPCookieStorage");
public static final long /*int*/ sel_sharedPrintInfo = sel_registerName("sharedPrintInfo");
public static final long /*int*/ sel_sharedWorkspace = sel_registerName("sharedWorkspace");
public static final long /*int*/ sel_shiftKey = sel_registerName("shiftKey");
public static final long /*int*/ sel_shouldAntialias = sel_registerName("shouldAntialias");
public static final long /*int*/ sel_shouldChangeTextInRange_replacementString_ = sel_registerName("shouldChangeTextInRange:replacementString:");
public static final long /*int*/ sel_shouldDelayWindowOrderingForEvent_ = sel_registerName("shouldDelayWindowOrderingForEvent:");
public static final long /*int*/ sel_shouldDrawInsertionPoint = sel_registerName("shouldDrawInsertionPoint");
public static final long /*int*/ sel_size = sel_registerName("size");
public static final long /*int*/ sel_sizeOfLabel_ = sel_registerName("sizeOfLabel:");
public static final long /*int*/ sel_sizeToFit = sel_registerName("sizeToFit");
public static final long /*int*/ sel_sizeValue = sel_registerName("sizeValue");
public static final long /*int*/ sel_skipDescendents = sel_registerName("skipDescendents");
public static final long /*int*/ sel_smallSystemFontSize = sel_registerName("smallSystemFontSize");
public static final long /*int*/ sel_sortIndicatorRectForBounds_ = sel_registerName("sortIndicatorRectForBounds:");
public static final long /*int*/ sel_standardPreferences = sel_registerName("standardPreferences");
public static final long /*int*/ sel_standardUserDefaults = sel_registerName("standardUserDefaults");
public static final long /*int*/ sel_standardWindowButton_ = sel_registerName("standardWindowButton:");
public static final long /*int*/ sel_startAnimation_ = sel_registerName("startAnimation:");
public static final long /*int*/ sel_state = sel_registerName("state");
public static final long /*int*/ sel_statusItemWithLength_ = sel_registerName("statusItemWithLength:");
public static final long /*int*/ sel_stop_ = sel_registerName("stop:");
public static final long /*int*/ sel_stopAnimation_ = sel_registerName("stopAnimation:");
public static final long /*int*/ sel_stopLoading_ = sel_registerName("stopLoading:");
public static final long /*int*/ sel_string = sel_registerName("string");
public static final long /*int*/ sel_stringByAddingPercentEscapesUsingEncoding_ = sel_registerName("stringByAddingPercentEscapesUsingEncoding:");
public static final long /*int*/ sel_stringByAppendingPathComponent_ = sel_registerName("stringByAppendingPathComponent:");
public static final long /*int*/ sel_stringByAppendingPathExtension_ = sel_registerName("stringByAppendingPathExtension:");
public static final long /*int*/ sel_stringByAppendingString_ = sel_registerName("stringByAppendingString:");
public static final long /*int*/ sel_stringByDeletingLastPathComponent = sel_registerName("stringByDeletingLastPathComponent");
public static final long /*int*/ sel_stringByDeletingPathExtension = sel_registerName("stringByDeletingPathExtension");
public static final long /*int*/ sel_stringByReplacingOccurrencesOfString_withString_ = sel_registerName("stringByReplacingOccurrencesOfString:withString:");
public static final long /*int*/ sel_stringByReplacingPercentEscapesUsingEncoding_ = sel_registerName("stringByReplacingPercentEscapesUsingEncoding:");
public static final long /*int*/ sel_stringForObjectValue_ = sel_registerName("stringForObjectValue:");
public static final long /*int*/ sel_stringForType_ = sel_registerName("stringForType:");
public static final long /*int*/ sel_stringValue = sel_registerName("stringValue");
public static final long /*int*/ sel_stringWithCharacters_length_ = sel_registerName("stringWithCharacters:length:");
public static final long /*int*/ sel_stringWithFormat_ = sel_registerName("stringWithFormat:");
public static final long /*int*/ sel_stringWithUTF8String_ = sel_registerName("stringWithUTF8String:");
public static final long /*int*/ sel_stroke = sel_registerName("stroke");
public static final long /*int*/ sel_strokeRect_ = sel_registerName("strokeRect:");
public static final long /*int*/ sel_styleMask = sel_registerName("styleMask");
public static final long /*int*/ sel_submenu = sel_registerName("submenu");
public static final long /*int*/ sel_subviews = sel_registerName("subviews");
public static final long /*int*/ sel_superclass = sel_registerName("superclass");
public static final long /*int*/ sel_superview = sel_registerName("superview");
public static final long /*int*/ sel_swipeWithEvent_ = sel_registerName("swipeWithEvent:");
public static final long /*int*/ sel_systemFontOfSize_ = sel_registerName("systemFontOfSize:");
public static final long /*int*/ sel_systemFontSize = sel_registerName("systemFontSize");
public static final long /*int*/ sel_systemFontSizeForControlSize_ = sel_registerName("systemFontSizeForControlSize:");
public static final long /*int*/ sel_systemStatusBar = sel_registerName("systemStatusBar");
public static final long /*int*/ sel_systemVersion = sel_registerName("systemVersion");
public static final long /*int*/ sel_tabStopType = sel_registerName("tabStopType");
public static final long /*int*/ sel_tabStops = sel_registerName("tabStops");
public static final long /*int*/ sel_tabView_didSelectTabViewItem_ = sel_registerName("tabView:didSelectTabViewItem:");
public static final long /*int*/ sel_tabView_shouldSelectTabViewItem_ = sel_registerName("tabView:shouldSelectTabViewItem:");
public static final long /*int*/ sel_tabView_willSelectTabViewItem_ = sel_registerName("tabView:willSelectTabViewItem:");
public static final long /*int*/ sel_tabViewItemAtPoint_ = sel_registerName("tabViewItemAtPoint:");
public static final long /*int*/ sel_tableColumns = sel_registerName("tableColumns");
public static final long /*int*/ sel_tableView_acceptDrop_row_dropOperation_ = sel_registerName("tableView:acceptDrop:row:dropOperation:");
public static final long /*int*/ sel_tableView_didClickTableColumn_ = sel_registerName("tableView:didClickTableColumn:");
public static final long /*int*/ sel_tableView_objectValueForTableColumn_row_ = sel_registerName("tableView:objectValueForTableColumn:row:");
public static final long /*int*/ sel_tableView_setObjectValue_forTableColumn_row_ = sel_registerName("tableView:setObjectValue:forTableColumn:row:");
public static final long /*int*/ sel_tableView_shouldEditTableColumn_row_ = sel_registerName("tableView:shouldEditTableColumn:row:");
public static final long /*int*/ sel_tableView_shouldReorderColumn_toColumn_ = sel_registerName("tableView:shouldReorderColumn:toColumn:");
public static final long /*int*/ sel_tableView_shouldSelectRow_ = sel_registerName("tableView:shouldSelectRow:");
public static final long /*int*/ sel_tableView_shouldTrackCell_forTableColumn_row_ = sel_registerName("tableView:shouldTrackCell:forTableColumn:row:");
public static final long /*int*/ sel_tableView_validateDrop_proposedRow_proposedDropOperation_ = sel_registerName("tableView:validateDrop:proposedRow:proposedDropOperation:");
public static final long /*int*/ sel_tableView_willDisplayCell_forTableColumn_row_ = sel_registerName("tableView:willDisplayCell:forTableColumn:row:");
public static final long /*int*/ sel_tableView_writeRowsWithIndexes_toPasteboard_ = sel_registerName("tableView:writeRowsWithIndexes:toPasteboard:");
public static final long /*int*/ sel_tableViewColumnDidMove_ = sel_registerName("tableViewColumnDidMove:");
public static final long /*int*/ sel_tableViewColumnDidResize_ = sel_registerName("tableViewColumnDidResize:");
public static final long /*int*/ sel_tableViewSelectionDidChange_ = sel_registerName("tableViewSelectionDidChange:");
public static final long /*int*/ sel_tableViewSelectionIsChanging_ = sel_registerName("tableViewSelectionIsChanging:");
public static final long /*int*/ sel_tag = sel_registerName("tag");
public static final long /*int*/ sel_target = sel_registerName("target");
public static final long /*int*/ sel_terminate_ = sel_registerName("terminate:");
public static final long /*int*/ sel_testPart_ = sel_registerName("testPart:");
public static final long /*int*/ sel_textBackgroundColor = sel_registerName("textBackgroundColor");
public static final long /*int*/ sel_textColor = sel_registerName("textColor");
public static final long /*int*/ sel_textContainer = sel_registerName("textContainer");
public static final long /*int*/ sel_textDidChange_ = sel_registerName("textDidChange:");
public static final long /*int*/ sel_textDidEndEditing_ = sel_registerName("textDidEndEditing:");
public static final long /*int*/ sel_textStorage = sel_registerName("textStorage");
public static final long /*int*/ sel_textView_clickedOnLink_atIndex_ = sel_registerName("textView:clickedOnLink:atIndex:");
public static final long /*int*/ sel_textView_willChangeSelectionFromCharacterRange_toCharacterRange_ = sel_registerName("textView:willChangeSelectionFromCharacterRange:toCharacterRange:");
public static final long /*int*/ sel_textViewDidChangeSelection_ = sel_registerName("textViewDidChangeSelection:");
public static final long /*int*/ sel_thickness = sel_registerName("thickness");
public static final long /*int*/ sel_threadDictionary = sel_registerName("threadDictionary");
public static final long /*int*/ sel_tile = sel_registerName("tile");
public static final long /*int*/ sel_timeZone = sel_registerName("timeZone");
public static final long /*int*/ sel_timestamp = sel_registerName("timestamp");
public static final long /*int*/ sel_title = sel_registerName("title");
public static final long /*int*/ sel_titleCell = sel_registerName("titleCell");
public static final long /*int*/ sel_titleFont = sel_registerName("titleFont");
public static final long /*int*/ sel_titleOfSelectedItem = sel_registerName("titleOfSelectedItem");
public static final long /*int*/ sel_titleRectForBounds_ = sel_registerName("titleRectForBounds:");
public static final long /*int*/ sel_toggleToolbarShown_ = sel_registerName("toggleToolbarShown:");
public static final long /*int*/ sel_toolbar = sel_registerName("toolbar");
public static final long /*int*/ sel_toolbar_itemForItemIdentifier_willBeInsertedIntoToolbar_ = sel_registerName("toolbar:itemForItemIdentifier:willBeInsertedIntoToolbar:");
public static final long /*int*/ sel_toolbarAllowedItemIdentifiers_ = sel_registerName("toolbarAllowedItemIdentifiers:");
public static final long /*int*/ sel_toolbarDefaultItemIdentifiers_ = sel_registerName("toolbarDefaultItemIdentifiers:");
public static final long /*int*/ sel_toolbarDidRemoveItem_ = sel_registerName("toolbarDidRemoveItem:");
public static final long /*int*/ sel_toolbarSelectableItemIdentifiers_ = sel_registerName("toolbarSelectableItemIdentifiers:");
public static final long /*int*/ sel_toolbarWillAddItem_ = sel_registerName("toolbarWillAddItem:");
public static final long /*int*/ sel_touchesBeganWithEvent_ = sel_registerName("touchesBeganWithEvent:");
public static final long /*int*/ sel_touchesCancelledWithEvent_ = sel_registerName("touchesCancelledWithEvent:");
public static final long /*int*/ sel_touchesEndedWithEvent_ = sel_registerName("touchesEndedWithEvent:");
public static final long /*int*/ sel_touchesMatchingPhase_inView_ = sel_registerName("touchesMatchingPhase:inView:");
public static final long /*int*/ sel_touchesMovedWithEvent_ = sel_registerName("touchesMovedWithEvent:");
public static final long /*int*/ sel_trackingAreas = sel_registerName("trackingAreas");
public static final long /*int*/ sel_traitsOfFont_ = sel_registerName("traitsOfFont:");
public static final long /*int*/ sel_transform = sel_registerName("transform");
public static final long /*int*/ sel_transformPoint_ = sel_registerName("transformPoint:");
public static final long /*int*/ sel_transformSize_ = sel_registerName("transformSize:");
public static final long /*int*/ sel_transformStruct = sel_registerName("transformStruct");
public static final long /*int*/ sel_transformUsingAffineTransform_ = sel_registerName("transformUsingAffineTransform:");
public static final long /*int*/ sel_translateXBy_yBy_ = sel_registerName("translateXBy:yBy:");
public static final long /*int*/ sel_type = sel_registerName("type");
public static final long /*int*/ sel_type_conformsToType_ = sel_registerName("type:conformsToType:");
public static final long /*int*/ sel_typeOfFile_error_ = sel_registerName("typeOfFile:error:");
public static final long /*int*/ sel_types = sel_registerName("types");
public static final long /*int*/ sel_typesetter = sel_registerName("typesetter");
public static final long /*int*/ sel_unarchiveObjectWithData_ = sel_registerName("unarchiveObjectWithData:");
public static final long /*int*/ sel_undefined = sel_registerName("undefined");
public static final long /*int*/ sel_undo = sel_registerName("undo");
public static final long /*int*/ sel_undoManager = sel_registerName("undoManager");
public static final long /*int*/ sel_unhideAllApplications_ = sel_registerName("unhideAllApplications:");
public static final long /*int*/ sel_unlockFocus = sel_registerName("unlockFocus");
public static final long /*int*/ sel_unmarkText = sel_registerName("unmarkText");
public static final long /*int*/ sel_unregisterDraggedTypes = sel_registerName("unregisterDraggedTypes");
public static final long /*int*/ sel_update = sel_registerName("update");
public static final long /*int*/ sel_updateFromPMPrintSettings = sel_registerName("updateFromPMPrintSettings");
public static final long /*int*/ sel_updateTrackingAreas = sel_registerName("updateTrackingAreas");
public static final long /*int*/ sel_use = sel_registerName("use");
public static final long /*int*/ sel_useCredential_forAuthenticationChallenge_ = sel_registerName("useCredential:forAuthenticationChallenge:");
public static final long /*int*/ sel_usedRectForTextContainer_ = sel_registerName("usedRectForTextContainer:");
public static final long /*int*/ sel_user = sel_registerName("user");
public static final long /*int*/ sel_userInfo = sel_registerName("userInfo");
public static final long /*int*/ sel_userSpaceScaleFactor = sel_registerName("userSpaceScaleFactor");
public static final long /*int*/ sel_usesAlternatingRowBackgroundColors = sel_registerName("usesAlternatingRowBackgroundColors");
public static final long /*int*/ sel_validAttributesForMarkedText = sel_registerName("validAttributesForMarkedText");
public static final long /*int*/ sel_validModesForFontPanel_ = sel_registerName("validModesForFontPanel:");
public static final long /*int*/ sel_validRequestorForSendType_returnType_ = sel_registerName("validRequestorForSendType:returnType:");
public static final long /*int*/ sel_validateMenuItem_ = sel_registerName("validateMenuItem:");
public static final long /*int*/ sel_validateVisibleColumns = sel_registerName("validateVisibleColumns");
public static final long /*int*/ sel_value = sel_registerName("value");
public static final long /*int*/ sel_valueForKey_ = sel_registerName("valueForKey:");
public static final long /*int*/ sel_valueWithPoint_ = sel_registerName("valueWithPoint:");
public static final long /*int*/ sel_valueWithRange_ = sel_registerName("valueWithRange:");
public static final long /*int*/ sel_valueWithRect_ = sel_registerName("valueWithRect:");
public static final long /*int*/ sel_valueWithSize_ = sel_registerName("valueWithSize:");
public static final long /*int*/ sel_view = sel_registerName("view");
public static final long /*int*/ sel_view_stringForToolTip_point_userData_ = sel_registerName("view:stringForToolTip:point:userData:");
public static final long /*int*/ sel_viewDidMoveToWindow = sel_registerName("viewDidMoveToWindow");
public static final long /*int*/ sel_viewWillMoveToWindow_ = sel_registerName("viewWillMoveToWindow:");
public static final long /*int*/ sel_visibleFrame = sel_registerName("visibleFrame");
public static final long /*int*/ sel_visibleRect = sel_registerName("visibleRect");
public static final long /*int*/ sel_wantsPeriodicDraggingUpdates = sel_registerName("wantsPeriodicDraggingUpdates");
public static final long /*int*/ sel_wantsToHandleMouseEvents = sel_registerName("wantsToHandleMouseEvents");
public static final long /*int*/ sel_webFrame = sel_registerName("webFrame");
public static final long /*int*/ sel_webScriptValueAtIndex_ = sel_registerName("webScriptValueAtIndex:");
public static final long /*int*/ sel_webView_contextMenuItemsForElement_defaultMenuItems_ = sel_registerName("webView:contextMenuItemsForElement:defaultMenuItems:");
public static final long /*int*/ sel_webView_createWebViewWithRequest_ = sel_registerName("webView:createWebViewWithRequest:");
public static final long /*int*/ sel_webView_decidePolicyForMIMEType_request_frame_decisionListener_ = sel_registerName("webView:decidePolicyForMIMEType:request:frame:decisionListener:");
public static final long /*int*/ sel_webView_decidePolicyForNavigationAction_request_frame_decisionListener_ = sel_registerName("webView:decidePolicyForNavigationAction:request:frame:decisionListener:");
public static final long /*int*/ sel_webView_decidePolicyForNewWindowAction_request_newFrameName_decisionListener_ = sel_registerName("webView:decidePolicyForNewWindowAction:request:newFrameName:decisionListener:");
public static final long /*int*/ sel_webView_didChangeLocationWithinPageForFrame_ = sel_registerName("webView:didChangeLocationWithinPageForFrame:");
public static final long /*int*/ sel_webView_didCommitLoadForFrame_ = sel_registerName("webView:didCommitLoadForFrame:");
public static final long /*int*/ sel_webView_didFailProvisionalLoadWithError_forFrame_ = sel_registerName("webView:didFailProvisionalLoadWithError:forFrame:");
public static final long /*int*/ sel_webView_didFinishLoadForFrame_ = sel_registerName("webView:didFinishLoadForFrame:");
public static final long /*int*/ sel_webView_didReceiveTitle_forFrame_ = sel_registerName("webView:didReceiveTitle:forFrame:");
public static final long /*int*/ sel_webView_didStartProvisionalLoadForFrame_ = sel_registerName("webView:didStartProvisionalLoadForFrame:");
public static final long /*int*/ sel_webView_identifierForInitialRequest_fromDataSource_ = sel_registerName("webView:identifierForInitialRequest:fromDataSource:");
public static final long /*int*/ sel_webView_mouseDidMoveOverElement_modifierFlags_ = sel_registerName("webView:mouseDidMoveOverElement:modifierFlags:");
public static final long /*int*/ sel_webView_printFrameView_ = sel_registerName("webView:printFrameView:");
public static final long /*int*/ sel_webView_resource_didFailLoadingWithError_fromDataSource_ = sel_registerName("webView:resource:didFailLoadingWithError:fromDataSource:");
public static final long /*int*/ sel_webView_resource_didFinishLoadingFromDataSource_ = sel_registerName("webView:resource:didFinishLoadingFromDataSource:");
public static final long /*int*/ sel_webView_resource_didReceiveAuthenticationChallenge_fromDataSource_ = sel_registerName("webView:resource:didReceiveAuthenticationChallenge:fromDataSource:");
public static final long /*int*/ sel_webView_resource_willSendRequest_redirectResponse_fromDataSource_ = sel_registerName("webView:resource:willSendRequest:redirectResponse:fromDataSource:");
public static final long /*int*/ sel_webView_runBeforeUnloadConfirmPanelWithMessage_initiatedByFrame_ = sel_registerName("webView:runBeforeUnloadConfirmPanelWithMessage:initiatedByFrame:");
public static final long /*int*/ sel_webView_runJavaScriptAlertPanelWithMessage_ = sel_registerName("webView:runJavaScriptAlertPanelWithMessage:");
public static final long /*int*/ sel_webView_runJavaScriptAlertPanelWithMessage_initiatedByFrame_ = sel_registerName("webView:runJavaScriptAlertPanelWithMessage:initiatedByFrame:");
public static final long /*int*/ sel_webView_runJavaScriptConfirmPanelWithMessage_ = sel_registerName("webView:runJavaScriptConfirmPanelWithMessage:");
public static final long /*int*/ sel_webView_runJavaScriptConfirmPanelWithMessage_initiatedByFrame_ = sel_registerName("webView:runJavaScriptConfirmPanelWithMessage:initiatedByFrame:");
public static final long /*int*/ sel_webView_runOpenPanelForFileButtonWithResultListener_ = sel_registerName("webView:runOpenPanelForFileButtonWithResultListener:");
public static final long /*int*/ sel_webView_setFrame_ = sel_registerName("webView:setFrame:");
public static final long /*int*/ sel_webView_setResizable_ = sel_registerName("webView:setResizable:");
public static final long /*int*/ sel_webView_setStatusBarVisible_ = sel_registerName("webView:setStatusBarVisible:");
public static final long /*int*/ sel_webView_setStatusText_ = sel_registerName("webView:setStatusText:");
public static final long /*int*/ sel_webView_setToolbarsVisible_ = sel_registerName("webView:setToolbarsVisible:");
public static final long /*int*/ sel_webView_unableToImplementPolicyWithError_frame_ = sel_registerName("webView:unableToImplementPolicyWithError:frame:");
public static final long /*int*/ sel_webView_windowScriptObjectAvailable_ = sel_registerName("webView:windowScriptObjectAvailable:");
public static final long /*int*/ sel_webViewClose_ = sel_registerName("webViewClose:");
public static final long /*int*/ sel_webViewFocus_ = sel_registerName("webViewFocus:");
public static final long /*int*/ sel_webViewShow_ = sel_registerName("webViewShow:");
public static final long /*int*/ sel_webViewUnfocus_ = sel_registerName("webViewUnfocus:");
public static final long /*int*/ sel_weightOfFont_ = sel_registerName("weightOfFont:");
public static final long /*int*/ sel_wheelDelta = sel_registerName("wheelDelta");
public static final long /*int*/ sel_width = sel_registerName("width");
public static final long /*int*/ sel_window = sel_registerName("window");
public static final long /*int*/ sel_windowBackgroundColor = sel_registerName("windowBackgroundColor");
public static final long /*int*/ sel_windowDidBecomeKey_ = sel_registerName("windowDidBecomeKey:");
public static final long /*int*/ sel_windowDidDeminiaturize_ = sel_registerName("windowDidDeminiaturize:");
public static final long /*int*/ sel_windowDidMiniaturize_ = sel_registerName("windowDidMiniaturize:");
public static final long /*int*/ sel_windowDidMove_ = sel_registerName("windowDidMove:");
public static final long /*int*/ sel_windowDidResignKey_ = sel_registerName("windowDidResignKey:");
public static final long /*int*/ sel_windowDidResize_ = sel_registerName("windowDidResize:");
public static final long /*int*/ sel_windowFrameColor = sel_registerName("windowFrameColor");
public static final long /*int*/ sel_windowFrameTextColor = sel_registerName("windowFrameTextColor");
public static final long /*int*/ sel_windowNumber = sel_registerName("windowNumber");
public static final long /*int*/ sel_windowNumberAtPoint_belowWindowWithWindowNumber_ = sel_registerName("windowNumberAtPoint:belowWindowWithWindowNumber:");
public static final long /*int*/ sel_windowRef = sel_registerName("windowRef");
public static final long /*int*/ sel_windowShouldClose_ = sel_registerName("windowShouldClose:");
public static final long /*int*/ sel_windowWillClose_ = sel_registerName("windowWillClose:");
public static final long /*int*/ sel_windowWithWindowNumber_ = sel_registerName("windowWithWindowNumber:");
public static final long /*int*/ sel_windows = sel_registerName("windows");
public static final long /*int*/ sel_worksWhenModal = sel_registerName("worksWhenModal");
public static final long /*int*/ sel_wraps = sel_registerName("wraps");
public static final long /*int*/ sel_writeSelectionToPasteboard_types_ = sel_registerName("writeSelectionToPasteboard:types:");
public static final long /*int*/ sel_writeToPasteboard_ = sel_registerName("writeToPasteboard:");
public static final long /*int*/ sel_yearOfCommonEra = sel_registerName("yearOfCommonEra");
public static final long /*int*/ sel_zoom_ = sel_registerName("zoom:");

/** Constants */
public static final int NSAlertFirstButtonReturn = 1000;
public static final int NSAlertSecondButtonReturn = 1001;
public static final int NSAlertThirdButtonReturn = 1002;
public static final int NSAlphaFirstBitmapFormat = 1;
public static final int NSAlphaNonpremultipliedBitmapFormat = 2;
public static final int NSAlternateKeyMask = 524288;
public static final int NSAppKitDefined = 13;
public static final int NSApplicationDefined = 15;
public static final int NSApplicationDelegateReplySuccess = 0;
public static final int NSAtTop = 2;
public static final int NSBackgroundStyleRaised = 2;
public static final int NSBackingStoreBuffered = 2;
public static final int NSBackspaceCharacter = 8;
public static final int NSBevelLineJoinStyle = 2;
public static final int NSBezelBorder = 2;
public static final int NSBoldFontMask = 2;
public static final int NSBorderlessWindowMask = 0;
public static final int NSBottomTabsBezelBorder = 2;
public static final int NSBoxCustom = 4;
public static final int NSBoxSeparator = 2;
public static final int NSButtLineCapStyle = 0;
public static final int NSCancelButton = 0;
public static final int NSCarriageReturnCharacter = 13;
public static final int NSCenterTextAlignment = 2;
public static final int NSClockAndCalendarDatePickerStyle = 1;
public static final int NSClosableWindowMask = 2;
public static final int NSClosePathBezierPathElement = 3;
public static final int NSCommandKeyMask = 1048576;
public static final int NSCompositeClear = 0;
public static final int NSCompositeCopy = 1;
public static final int NSCompositeSourceAtop = 5;
public static final int NSCompositeSourceOver = 2;
public static final int NSCompositeXOR = 10;
public static final int NSContentsCellMask = 1;
public static final int NSControlKeyMask = 262144;
public static final int NSCriticalAlertStyle = 2;
public static final int NSCurveToBezierPathElement = 2;
public static final int NSDeleteCharacter = 127;
public static final long NSDeviceIndependentModifierFlagsMask = 4294901760L;
public static final int NSDocModalWindowMask = 64;
public static final int NSDragOperationCopy = 1;
public static final int NSDragOperationDelete = 32;
public static final int NSDragOperationEvery = -1;
public static final int NSDragOperationLink = 2;
public static final int NSDragOperationMove = 16;
public static final int NSDragOperationNone = 0;
public static final int NSEnterCharacter = 3;
public static final int NSEvenOddWindingRule = 1;
public static final int NSEventTypeBeginGesture = 19;
public static final int NSEventTypeEndGesture = 20;
public static final int NSEventTypeGesture = 29;
public static final int NSEventTypeMagnify = 30;
public static final int NSEventTypeRotate = 18;
public static final int NSEventTypeSwipe = 31;
public static final int NSFileHandlingPanelOKButton = 1;
public static final int NSFlagsChanged = 12;
public static final int NSFocusRingTypeNone = 1;
public static final int NSFontPanelAllEffectsModeMask = 1048320;
public static final int NSFontPanelAllModesMask = -1;
public static final int NSFontPanelCollectionModeMask = 4;
public static final int NSFontPanelDocumentColorEffectModeMask = 2048;
public static final int NSFontPanelFaceModeMask = 1;
public static final int NSFontPanelShadowEffectModeMask = 4096;
public static final int NSFontPanelSizeModeMask = 2;
public static final int NSFontPanelStandardModesMask = 65535;
public static final int NSFontPanelStrikethroughEffectModeMask = 512;
public static final int NSFontPanelTextColorEffectModeMask = 1024;
public static final int NSFontPanelUnderlineEffectModeMask = 256;
public static final int NSHelpFunctionKey = 63302;
public static final int NSHelpKeyMask = 4194304;
public static final int NSHourMinuteDatePickerElementFlag = 12;
public static final int NSHourMinuteSecondDatePickerElementFlag = 14;
public static final int NSImageAbove = 5;
public static final int NSImageAlignCenter = 0;
public static final int NSImageAlignLeft = 4;
public static final int NSImageAlignRight = 8;
public static final int NSImageCacheNever = 3;
public static final int NSImageInterpolationDefault = 0;
public static final int NSImageInterpolationHigh = 3;
public static final int NSImageInterpolationLow = 2;
public static final int NSImageInterpolationNone = 1;
public static final int NSImageLeft = 2;
public static final int NSImageOnly = 1;
public static final int NSImageOverlaps = 6;
public static final int NSInformationalAlertStyle = 1;
public static final int NSItalicFontMask = 1;
public static final int NSJustifiedTextAlignment = 3;
public static final int NSKeyDown = 10;
public static final int NSKeyUp = 11;
public static final int NSLandscapeOrientation = 1;
public static final int NSLeftMouseDown = 1;
public static final int NSLeftMouseDownMask = 2;
public static final int NSLeftMouseDragged = 6;
public static final int NSLeftMouseDraggedMask = 64;
public static final int NSLeftMouseUp = 2;
public static final int NSLeftMouseUpMask = 4;
public static final int NSLeftTabStopType = 0;
public static final int NSLeftTextAlignment = 0;
public static final int NSLineBreakByClipping = 2;
public static final int NSLineBreakByTruncatingMiddle = 5;
public static final int NSLineBreakByTruncatingTail = 4;
public static final int NSLineBreakByWordWrapping = 0;
public static final int NSLineToBezierPathElement = 1;
public static final int NSMiniControlSize = 2;
public static final int NSMiniaturizableWindowMask = 4;
public static final int NSMiterLineJoinStyle = 0;
public static final int NSMixedState = -1;
public static final int NSModalPanelWindowLevel = 8;
public static final int NSMomentaryLightButton = 0;
public static final int NSMouseEntered = 8;
public static final int NSMouseExited = 9;
public static final int NSMouseMoved = 5;
public static final int NSMoveToBezierPathElement = 0;
public static final int NSNewlineCharacter = 10;
public static final int NSNoBorder = 0;
public static final int NSNoImage = 0;
public static final int NSNoTitle = 0;
public static final int NSNonZeroWindingRule = 0;
public static final int NSNonactivatingPanelMask = 128;
public static final int NSNormalWindowLevel = 0;
public static final int NSOffState = 0;
public static final int NSOnState = 1;
public static final int NSOpenGLCPSurfaceOrder = 235;
public static final int NSOpenGLCPSwapInterval = 222;
public static final int NSOpenGLPFAAccumSize = 14;
public static final int NSOpenGLPFAAlphaSize = 11;
public static final int NSOpenGLPFAColorSize = 8;
public static final int NSOpenGLPFADepthSize = 12;
public static final int NSOpenGLPFADoubleBuffer = 5;
public static final int NSOpenGLPFASampleBuffers = 55;
public static final int NSOpenGLPFASamples = 56;
public static final int NSOpenGLPFAStencilSize = 13;
public static final int NSOpenGLPFAStereo = 6;
public static final int NSOtherMouseDown = 25;
public static final int NSOtherMouseDragged = 27;
public static final int NSOtherMouseUp = 26;
public static final int NSOutlineViewDropOnItemIndex = -1;
public static final int NSPageDownFunctionKey = 63277;
public static final int NSPageUpFunctionKey = 63276;
public static final int NSPortraitOrientation = 0;
public static final int NSPrintPanelShowsCopies = 1;
public static final int NSPrintPanelShowsOrientation = 8;
public static final int NSPrintPanelShowsPageRange = 2;
public static final int NSPrintPanelShowsPageSetupAccessory = 256;
public static final int NSProgressIndicatorPreferredThickness = 14;
public static final int NSPushOnPushOffButton = 1;
public static final int NSRGBColorSpaceModel = 1;
public static final int NSRadioButton = 4;
public static final int NSRegularControlSize = 0;
public static final int NSRegularSquareBezelStyle = 2;
public static final int NSResizableWindowMask = 8;
public static final int NSRightMouseDown = 3;
public static final int NSRightMouseDragged = 7;
public static final int NSRightMouseUp = 4;
public static final int NSRightTextAlignment = 1;
public static final int NSRoundLineCapStyle = 1;
public static final int NSRoundLineJoinStyle = 1;
public static final int NSRoundedBezelStyle = 1;
public static final int NSRoundedDisclosureBezelStyle = 14;
public static final int NSScaleNone = 2;
public static final int NSScrollWheel = 22;
public static final int NSScrollerDecrementLine = 4;
public static final int NSScrollerDecrementPage = 1;
public static final int NSScrollerIncrementLine = 5;
public static final int NSScrollerIncrementPage = 3;
public static final int NSScrollerKnob = 2;
public static final int NSScrollerKnobSlot = 6;
public static final int NSShadowlessSquareBezelStyle = 6;
public static final int NSShiftKeyMask = 131072;
public static final int NSSmallControlSize = 1;
public static final int NSSquareLineCapStyle = 2;
public static final int NSSquareStatusItemLength = -2;
public static final int NSStatusWindowLevel = 25;
public static final int NSStringDrawingUsesLineFragmentOrigin = 1;
public static final int NSSubmenuWindowLevel = 3;
public static final int NSSwitchButton = 3;
public static final int NSSystemDefined = 14;
public static final int NSTabCharacter = 9;
public static final int NSTableColumnNoResizing = 0;
public static final int NSTableColumnUserResizingMask = 2;
public static final int NSTableViewDropAbove = 1;
public static final int NSTableViewDropOn = 0;
public static final int NSTableViewGridNone = 0;
public static final int NSTableViewNoColumnAutoresizing = 0;
public static final int NSTableViewSolidVerticalGridLineMask = 1;
public static final int NSTerminateCancel = 0;
public static final int NSTerminateNow = 1;
public static final int NSTextFieldAndStepperDatePickerStyle = 0;
public static final int NSTextFieldDatePickerStyle = 2;
public static final int NSTitledWindowMask = 1;
public static final int NSToolbarDisplayModeIconOnly = 2;
public static final int NSTouchEventSubtype = 3;
public static final int NSTouchPhaseAny = -1;
public static final int NSTouchPhaseBegan = 1;
public static final int NSTouchPhaseCancelled = 16;
public static final int NSTouchPhaseEnded = 8;
public static final int NSTouchPhaseMoved = 2;
public static final int NSTouchPhaseStationary = 4;
public static final int NSTouchPhaseTouching = 7;
public static final int NSUnderlineStyleDouble = 9;
public static final int NSUnderlineStyleNone = 0;
public static final int NSUnderlineStyleSingle = 1;
public static final int NSUnderlineStyleThick = 2;
public static final int NSUtilityWindowMask = 16;
public static final int NSViewHeightSizable = 16;
public static final int NSViewMaxXMargin = 4;
public static final int NSViewMaxYMargin = 32;
public static final int NSViewMinXMargin = 1;
public static final int NSViewMinYMargin = 8;
public static final int NSViewWidthSizable = 2;
public static final int NSWarningAlertStyle = 0;
public static final int NSWindowAbove = 1;
public static final int NSWindowBelow = -1;
public static final int NSWindowCollectionBehaviorCanJoinAllSpaces = 1;
public static final int NSWindowCollectionBehaviorDefault = 0;
public static final int NSWindowCollectionBehaviorMoveToActiveSpace = 2;
public static final int NSWritingDirectionLeftToRight = 0;
public static final int NSWritingDirectionNatural = -1;
public static final int NSWritingDirectionRightToLeft = 1;
public static final int NSYearMonthDatePickerElementFlag = 192;
public static final int NSYearMonthDayDatePickerElementFlag = 224;
public static final int kCFRunLoopBeforeWaiting = 32;
public static final int kCFStringEncodingUTF8 = 134217984;
public static final int kCGBlendModeDifference = 10;
public static final int kCGBlendModeNormal = 0;
public static final int kCGEventFilterMaskPermitLocalKeyboardEvents = 2;
public static final int kCGEventFilterMaskPermitLocalMouseEvents = 1;
public static final int kCGEventFilterMaskPermitSystemDefinedEvents = 4;
public static final int kCGEventKeyDown = 10;
public static final int kCGEventKeyUp = 11;
public static final int kCGEventLeftMouseDown = 1;
public static final int kCGEventLeftMouseUp = 2;
public static final int kCGEventMouseMoved = 5;
public static final int kCGEventOtherMouseDown = 25;
public static final int kCGEventOtherMouseUp = 26;
public static final int kCGEventRightMouseDown = 3;
public static final int kCGEventRightMouseUp = 4;
public static final int kCGEventSourceStateHIDSystemState = 1;
public static final int kCGEventSuppressionStateRemoteMouseDrag = 1;
public static final int kCGEventSuppressionStateSuppressionInterval = 0;
public static final int kCGHIDEventTap = 0;
public static final int kCGImageAlphaFirst = 4;
public static final int kCGImageAlphaLast = 3;
public static final int kCGImageAlphaNoneSkipFirst = 6;
public static final int kCGImageAlphaNoneSkipLast = 5;
public static final int kCGImageAlphaOnly = 7;
public static final int kCGKeyboardEventKeyboardType = 10;
public static final int kCGLineCapButt = 0;
public static final int kCGLineCapRound = 1;
public static final int kCGLineCapSquare = 2;
public static final int kCGLineJoinBevel = 2;
public static final int kCGLineJoinMiter = 0;
public static final int kCGLineJoinRound = 1;
public static final int kCGPathElementAddCurveToPoint = 3;
public static final int kCGPathElementAddLineToPoint = 1;
public static final int kCGPathElementAddQuadCurveToPoint = 2;
public static final int kCGPathElementCloseSubpath = 4;
public static final int kCGPathElementMoveToPoint = 0;
public static final int kCGPathStroke = 2;
public static final int kCGScrollEventUnitLine = 1;
public static final int kCGScrollEventUnitPixel = 0;
public static final int kCGTextFillStroke = 2;
public static final int kCTParagraphStyleSpecifierBaseWritingDirection = 13;
public static final int kCTWritingDirectionLeftToRight = 0;
public static final int kCTWritingDirectionNatural = -1;
public static final int kCTWritingDirectionRightToLeft = 1;
public static final int NSAllApplicationsDirectory = 100;
public static final int NSAllDomainsMask = 65535;
public static final int NSCachesDirectory = 13;
public static final int NSNotFound = 2147483647;
public static final int NSOrderedSame = 0;
public static final int NSURLCredentialPersistenceForSession = 1;
public static final int NSURLErrorBadURL = -1000;
public static final int NSURLErrorSecureConnectionFailed = -1200;
public static final int NSURLErrorServerCertificateNotYetValid = -1204;
public static final int NSURLRequestReloadIgnoringLocalCacheData = 1;
public static final int NSUTF8StringEncoding = 4;
public static final int NSUserDomainMask = 1;

/** Globals */
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityAttributedStringForRangeParameterizedAttribute();
public static final NSString NSAccessibilityAttributedStringForRangeParameterizedAttribute = new NSString(NSAccessibilityAttributedStringForRangeParameterizedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityBackgroundColorTextAttribute();
public static final NSString NSAccessibilityBackgroundColorTextAttribute = new NSString(NSAccessibilityBackgroundColorTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityBoundsForRangeParameterizedAttribute();
public static final NSString NSAccessibilityBoundsForRangeParameterizedAttribute = new NSString(NSAccessibilityBoundsForRangeParameterizedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityButtonRole();
public static final NSString NSAccessibilityButtonRole = new NSString(NSAccessibilityButtonRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityCheckBoxRole();
public static final NSString NSAccessibilityCheckBoxRole = new NSString(NSAccessibilityCheckBoxRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityChildrenAttribute();
public static final NSString NSAccessibilityChildrenAttribute = new NSString(NSAccessibilityChildrenAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityColorWellRole();
public static final NSString NSAccessibilityColorWellRole = new NSString(NSAccessibilityColorWellRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityColumnRole();
public static final NSString NSAccessibilityColumnRole = new NSString(NSAccessibilityColumnRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityColumnsAttribute();
public static final NSString NSAccessibilityColumnsAttribute = new NSString(NSAccessibilityColumnsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityComboBoxRole();
public static final NSString NSAccessibilityComboBoxRole = new NSString(NSAccessibilityComboBoxRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityConfirmAction();
public static final NSString NSAccessibilityConfirmAction = new NSString(NSAccessibilityConfirmAction());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityContentsAttribute();
public static final NSString NSAccessibilityContentsAttribute = new NSString(NSAccessibilityContentsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityDescriptionAttribute();
public static final NSString NSAccessibilityDescriptionAttribute = new NSString(NSAccessibilityDescriptionAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityDialogSubrole();
public static final NSString NSAccessibilityDialogSubrole = new NSString(NSAccessibilityDialogSubrole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityEnabledAttribute();
public static final NSString NSAccessibilityEnabledAttribute = new NSString(NSAccessibilityEnabledAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityExpandedAttribute();
public static final NSString NSAccessibilityExpandedAttribute = new NSString(NSAccessibilityExpandedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityFloatingWindowSubrole();
public static final NSString NSAccessibilityFloatingWindowSubrole = new NSString(NSAccessibilityFloatingWindowSubrole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityFocusedAttribute();
public static final NSString NSAccessibilityFocusedAttribute = new NSString(NSAccessibilityFocusedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityFocusedUIElementChangedNotification();
public static final NSString NSAccessibilityFocusedUIElementChangedNotification = new NSString(NSAccessibilityFocusedUIElementChangedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityFocusedWindowChangedNotification();
public static final NSString NSAccessibilityFocusedWindowChangedNotification = new NSString(NSAccessibilityFocusedWindowChangedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityFontFamilyKey();
public static final NSString NSAccessibilityFontFamilyKey = new NSString(NSAccessibilityFontFamilyKey());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityFontNameKey();
public static final NSString NSAccessibilityFontNameKey = new NSString(NSAccessibilityFontNameKey());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityFontSizeKey();
public static final NSString NSAccessibilityFontSizeKey = new NSString(NSAccessibilityFontSizeKey());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityFontTextAttribute();
public static final NSString NSAccessibilityFontTextAttribute = new NSString(NSAccessibilityFontTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityForegroundColorTextAttribute();
public static final NSString NSAccessibilityForegroundColorTextAttribute = new NSString(NSAccessibilityForegroundColorTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityGridRole();
public static final NSString NSAccessibilityGridRole = new NSString(NSAccessibilityGridRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityGroupRole();
public static final NSString NSAccessibilityGroupRole = new NSString(NSAccessibilityGroupRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityHeaderAttribute();
public static final NSString NSAccessibilityHeaderAttribute = new NSString(NSAccessibilityHeaderAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityHelpAttribute();
public static final NSString NSAccessibilityHelpAttribute = new NSString(NSAccessibilityHelpAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityHelpTagRole();
public static final NSString NSAccessibilityHelpTagRole = new NSString(NSAccessibilityHelpTagRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityHorizontalOrientationValue();
public static final NSString NSAccessibilityHorizontalOrientationValue = new NSString(NSAccessibilityHorizontalOrientationValue());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityHorizontalScrollBarAttribute();
public static final NSString NSAccessibilityHorizontalScrollBarAttribute = new NSString(NSAccessibilityHorizontalScrollBarAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityImageRole();
public static final NSString NSAccessibilityImageRole = new NSString(NSAccessibilityImageRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityIncrementorRole();
public static final NSString NSAccessibilityIncrementorRole = new NSString(NSAccessibilityIncrementorRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityIndexAttribute();
public static final NSString NSAccessibilityIndexAttribute = new NSString(NSAccessibilityIndexAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityInsertionPointLineNumberAttribute();
public static final NSString NSAccessibilityInsertionPointLineNumberAttribute = new NSString(NSAccessibilityInsertionPointLineNumberAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityLabelValueAttribute();
public static final NSString NSAccessibilityLabelValueAttribute = new NSString(NSAccessibilityLabelValueAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityLineForIndexParameterizedAttribute();
public static final NSString NSAccessibilityLineForIndexParameterizedAttribute = new NSString(NSAccessibilityLineForIndexParameterizedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityLinkRole();
public static final NSString NSAccessibilityLinkRole = new NSString(NSAccessibilityLinkRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityLinkTextAttribute();
public static final NSString NSAccessibilityLinkTextAttribute = new NSString(NSAccessibilityLinkTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityLinkedUIElementsAttribute();
public static final NSString NSAccessibilityLinkedUIElementsAttribute = new NSString(NSAccessibilityLinkedUIElementsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityListRole();
public static final NSString NSAccessibilityListRole = new NSString(NSAccessibilityListRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityMaxValueAttribute();
public static final NSString NSAccessibilityMaxValueAttribute = new NSString(NSAccessibilityMaxValueAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityMenuBarRole();
public static final NSString NSAccessibilityMenuBarRole = new NSString(NSAccessibilityMenuBarRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityMenuButtonRole();
public static final NSString NSAccessibilityMenuButtonRole = new NSString(NSAccessibilityMenuButtonRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityMenuItemRole();
public static final NSString NSAccessibilityMenuItemRole = new NSString(NSAccessibilityMenuItemRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityMenuRole();
public static final NSString NSAccessibilityMenuRole = new NSString(NSAccessibilityMenuRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityMinValueAttribute();
public static final NSString NSAccessibilityMinValueAttribute = new NSString(NSAccessibilityMinValueAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityMisspelledTextAttribute();
public static final NSString NSAccessibilityMisspelledTextAttribute = new NSString(NSAccessibilityMisspelledTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityMovedNotification();
public static final NSString NSAccessibilityMovedNotification = new NSString(NSAccessibilityMovedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityNextContentsAttribute();
public static final NSString NSAccessibilityNextContentsAttribute = new NSString(NSAccessibilityNextContentsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityNumberOfCharactersAttribute();
public static final NSString NSAccessibilityNumberOfCharactersAttribute = new NSString(NSAccessibilityNumberOfCharactersAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityOrientationAttribute();
public static final NSString NSAccessibilityOrientationAttribute = new NSString(NSAccessibilityOrientationAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityOutlineRole();
public static final NSString NSAccessibilityOutlineRole = new NSString(NSAccessibilityOutlineRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityOutlineRowSubrole();
public static final NSString NSAccessibilityOutlineRowSubrole = new NSString(NSAccessibilityOutlineRowSubrole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityParentAttribute();
public static final NSString NSAccessibilityParentAttribute = new NSString(NSAccessibilityParentAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityPopUpButtonRole();
public static final NSString NSAccessibilityPopUpButtonRole = new NSString(NSAccessibilityPopUpButtonRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityPositionAttribute();
public static final NSString NSAccessibilityPositionAttribute = new NSString(NSAccessibilityPositionAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityPressAction();
public static final NSString NSAccessibilityPressAction = new NSString(NSAccessibilityPressAction());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityPreviousContentsAttribute();
public static final NSString NSAccessibilityPreviousContentsAttribute = new NSString(NSAccessibilityPreviousContentsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityProgressIndicatorRole();
public static final NSString NSAccessibilityProgressIndicatorRole = new NSString(NSAccessibilityProgressIndicatorRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRTFForRangeParameterizedAttribute();
public static final NSString NSAccessibilityRTFForRangeParameterizedAttribute = new NSString(NSAccessibilityRTFForRangeParameterizedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRadioButtonRole();
public static final NSString NSAccessibilityRadioButtonRole = new NSString(NSAccessibilityRadioButtonRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRadioGroupRole();
public static final NSString NSAccessibilityRadioGroupRole = new NSString(NSAccessibilityRadioGroupRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRangeForIndexParameterizedAttribute();
public static final NSString NSAccessibilityRangeForIndexParameterizedAttribute = new NSString(NSAccessibilityRangeForIndexParameterizedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRangeForLineParameterizedAttribute();
public static final NSString NSAccessibilityRangeForLineParameterizedAttribute = new NSString(NSAccessibilityRangeForLineParameterizedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRangeForPositionParameterizedAttribute();
public static final NSString NSAccessibilityRangeForPositionParameterizedAttribute = new NSString(NSAccessibilityRangeForPositionParameterizedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityResizedNotification();
public static final NSString NSAccessibilityResizedNotification = new NSString(NSAccessibilityResizedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRoleAttribute();
public static final NSString NSAccessibilityRoleAttribute = new NSString(NSAccessibilityRoleAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRoleDescriptionAttribute();
public static final NSString NSAccessibilityRoleDescriptionAttribute = new NSString(NSAccessibilityRoleDescriptionAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRowCountChangedNotification();
public static final NSString NSAccessibilityRowCountChangedNotification = new NSString(NSAccessibilityRowCountChangedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRowRole();
public static final NSString NSAccessibilityRowRole = new NSString(NSAccessibilityRowRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityRowsAttribute();
public static final NSString NSAccessibilityRowsAttribute = new NSString(NSAccessibilityRowsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityScrollAreaRole();
public static final NSString NSAccessibilityScrollAreaRole = new NSString(NSAccessibilityScrollAreaRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityScrollBarRole();
public static final NSString NSAccessibilityScrollBarRole = new NSString(NSAccessibilityScrollBarRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedAttribute();
public static final NSString NSAccessibilitySelectedAttribute = new NSString(NSAccessibilitySelectedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedChildrenAttribute();
public static final NSString NSAccessibilitySelectedChildrenAttribute = new NSString(NSAccessibilitySelectedChildrenAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedChildrenChangedNotification();
public static final NSString NSAccessibilitySelectedChildrenChangedNotification = new NSString(NSAccessibilitySelectedChildrenChangedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedColumnsAttribute();
public static final NSString NSAccessibilitySelectedColumnsAttribute = new NSString(NSAccessibilitySelectedColumnsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedRowsAttribute();
public static final NSString NSAccessibilitySelectedRowsAttribute = new NSString(NSAccessibilitySelectedRowsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedRowsChangedNotification();
public static final NSString NSAccessibilitySelectedRowsChangedNotification = new NSString(NSAccessibilitySelectedRowsChangedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedTextAttribute();
public static final NSString NSAccessibilitySelectedTextAttribute = new NSString(NSAccessibilitySelectedTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedTextChangedNotification();
public static final NSString NSAccessibilitySelectedTextChangedNotification = new NSString(NSAccessibilitySelectedTextChangedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedTextRangeAttribute();
public static final NSString NSAccessibilitySelectedTextRangeAttribute = new NSString(NSAccessibilitySelectedTextRangeAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySelectedTextRangesAttribute();
public static final NSString NSAccessibilitySelectedTextRangesAttribute = new NSString(NSAccessibilitySelectedTextRangesAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityServesAsTitleForUIElementsAttribute();
public static final NSString NSAccessibilityServesAsTitleForUIElementsAttribute = new NSString(NSAccessibilityServesAsTitleForUIElementsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityShowMenuAction();
public static final NSString NSAccessibilityShowMenuAction = new NSString(NSAccessibilityShowMenuAction());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySizeAttribute();
public static final NSString NSAccessibilitySizeAttribute = new NSString(NSAccessibilitySizeAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySliderRole();
public static final NSString NSAccessibilitySliderRole = new NSString(NSAccessibilitySliderRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySortButtonRole();
public static final NSString NSAccessibilitySortButtonRole = new NSString(NSAccessibilitySortButtonRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySplitterRole();
public static final NSString NSAccessibilitySplitterRole = new NSString(NSAccessibilitySplitterRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityStandardWindowSubrole();
public static final NSString NSAccessibilityStandardWindowSubrole = new NSString(NSAccessibilityStandardWindowSubrole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityStaticTextRole();
public static final NSString NSAccessibilityStaticTextRole = new NSString(NSAccessibilityStaticTextRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityStrikethroughColorTextAttribute();
public static final NSString NSAccessibilityStrikethroughColorTextAttribute = new NSString(NSAccessibilityStrikethroughColorTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityStrikethroughTextAttribute();
public static final NSString NSAccessibilityStrikethroughTextAttribute = new NSString(NSAccessibilityStrikethroughTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityStringForRangeParameterizedAttribute();
public static final NSString NSAccessibilityStringForRangeParameterizedAttribute = new NSString(NSAccessibilityStringForRangeParameterizedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityStyleRangeForIndexParameterizedAttribute();
public static final NSString NSAccessibilityStyleRangeForIndexParameterizedAttribute = new NSString(NSAccessibilityStyleRangeForIndexParameterizedAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySubroleAttribute();
public static final NSString NSAccessibilitySubroleAttribute = new NSString(NSAccessibilitySubroleAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySuperscriptTextAttribute();
public static final NSString NSAccessibilitySuperscriptTextAttribute = new NSString(NSAccessibilitySuperscriptTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilitySystemDialogSubrole();
public static final NSString NSAccessibilitySystemDialogSubrole = new NSString(NSAccessibilitySystemDialogSubrole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTabGroupRole();
public static final NSString NSAccessibilityTabGroupRole = new NSString(NSAccessibilityTabGroupRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTableRole();
public static final NSString NSAccessibilityTableRole = new NSString(NSAccessibilityTableRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTableRowSubrole();
public static final NSString NSAccessibilityTableRowSubrole = new NSString(NSAccessibilityTableRowSubrole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTabsAttribute();
public static final NSString NSAccessibilityTabsAttribute = new NSString(NSAccessibilityTabsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTextAreaRole();
public static final NSString NSAccessibilityTextAreaRole = new NSString(NSAccessibilityTextAreaRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTextFieldRole();
public static final NSString NSAccessibilityTextFieldRole = new NSString(NSAccessibilityTextFieldRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTextLinkSubrole();
public static final NSString NSAccessibilityTextLinkSubrole = new NSString(NSAccessibilityTextLinkSubrole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTitleAttribute();
public static final NSString NSAccessibilityTitleAttribute = new NSString(NSAccessibilityTitleAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTitleChangedNotification();
public static final NSString NSAccessibilityTitleChangedNotification = new NSString(NSAccessibilityTitleChangedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTitleUIElementAttribute();
public static final NSString NSAccessibilityTitleUIElementAttribute = new NSString(NSAccessibilityTitleUIElementAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityToolbarRole();
public static final NSString NSAccessibilityToolbarRole = new NSString(NSAccessibilityToolbarRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityTopLevelUIElementAttribute();
public static final NSString NSAccessibilityTopLevelUIElementAttribute = new NSString(NSAccessibilityTopLevelUIElementAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityURLAttribute();
public static final NSString NSAccessibilityURLAttribute = new NSString(NSAccessibilityURLAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityUnderlineColorTextAttribute();
public static final NSString NSAccessibilityUnderlineColorTextAttribute = new NSString(NSAccessibilityUnderlineColorTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityUnderlineTextAttribute();
public static final NSString NSAccessibilityUnderlineTextAttribute = new NSString(NSAccessibilityUnderlineTextAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityUnknownRole();
public static final NSString NSAccessibilityUnknownRole = new NSString(NSAccessibilityUnknownRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityUnknownSubrole();
public static final NSString NSAccessibilityUnknownSubrole = new NSString(NSAccessibilityUnknownSubrole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityValueAttribute();
public static final NSString NSAccessibilityValueAttribute = new NSString(NSAccessibilityValueAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityValueChangedNotification();
public static final NSString NSAccessibilityValueChangedNotification = new NSString(NSAccessibilityValueChangedNotification());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityValueDescriptionAttribute();
public static final NSString NSAccessibilityValueDescriptionAttribute = new NSString(NSAccessibilityValueDescriptionAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityValueIndicatorRole();
public static final NSString NSAccessibilityValueIndicatorRole = new NSString(NSAccessibilityValueIndicatorRole());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityVerticalOrientationValue();
public static final NSString NSAccessibilityVerticalOrientationValue = new NSString(NSAccessibilityVerticalOrientationValue());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityVerticalScrollBarAttribute();
public static final NSString NSAccessibilityVerticalScrollBarAttribute = new NSString(NSAccessibilityVerticalScrollBarAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityVisibleCharacterRangeAttribute();
public static final NSString NSAccessibilityVisibleCharacterRangeAttribute = new NSString(NSAccessibilityVisibleCharacterRangeAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityVisibleChildrenAttribute();
public static final NSString NSAccessibilityVisibleChildrenAttribute = new NSString(NSAccessibilityVisibleChildrenAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityVisibleColumnsAttribute();
public static final NSString NSAccessibilityVisibleColumnsAttribute = new NSString(NSAccessibilityVisibleColumnsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityVisibleNameKey();
public static final NSString NSAccessibilityVisibleNameKey = new NSString(NSAccessibilityVisibleNameKey());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityVisibleRowsAttribute();
public static final NSString NSAccessibilityVisibleRowsAttribute = new NSString(NSAccessibilityVisibleRowsAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityWindowAttribute();
public static final NSString NSAccessibilityWindowAttribute = new NSString(NSAccessibilityWindowAttribute());
/** @method flags=const */
public static final native long /*int*/ NSAccessibilityWindowRole();
public static final NSString NSAccessibilityWindowRole = new NSString(NSAccessibilityWindowRole());
/** @method flags=const */
public static final native long /*int*/ NSApplicationDidChangeScreenParametersNotification();
public static final NSString NSApplicationDidChangeScreenParametersNotification = new NSString(NSApplicationDidChangeScreenParametersNotification());
/** @method flags=const */
public static final native long /*int*/ NSAttachmentAttributeName();
public static final NSString NSAttachmentAttributeName = new NSString(NSAttachmentAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSBackgroundColorAttributeName();
public static final NSString NSBackgroundColorAttributeName = new NSString(NSBackgroundColorAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSBaselineOffsetAttributeName();
public static final NSString NSBaselineOffsetAttributeName = new NSString(NSBaselineOffsetAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSCalibratedRGBColorSpace();
public static final NSString NSCalibratedRGBColorSpace = new NSString(NSCalibratedRGBColorSpace());
/** @method flags=const */
public static final native long /*int*/ NSCursorAttributeName();
public static final NSString NSCursorAttributeName = new NSString(NSCursorAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSDeviceRGBColorSpace();
public static final NSString NSDeviceRGBColorSpace = new NSString(NSDeviceRGBColorSpace());
/** @method flags=const */
public static final native long /*int*/ NSDeviceResolution();
public static final NSString NSDeviceResolution = new NSString(NSDeviceResolution());
/** @method flags=const */
public static final native long /*int*/ NSDragPboard();
public static final NSString NSDragPboard = new NSString(NSDragPboard());
/** @method flags=const */
public static final native long /*int*/ NSEventTrackingRunLoopMode();
public static final NSString NSEventTrackingRunLoopMode = new NSString(NSEventTrackingRunLoopMode());
/** @method flags=const */
public static final native long /*int*/ NSFilenamesPboardType();
public static final NSString NSFilenamesPboardType = new NSString(NSFilenamesPboardType());
/** @method flags=const */
public static final native long /*int*/ NSFontAttributeName();
public static final NSString NSFontAttributeName = new NSString(NSFontAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSForegroundColorAttributeName();
public static final NSString NSForegroundColorAttributeName = new NSString(NSForegroundColorAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSHTMLPboardType();
public static final NSString NSHTMLPboardType = new NSString(NSHTMLPboardType());
/** @method flags=const */
public static final native long /*int*/ NSLigatureAttributeName();
public static final NSString NSLigatureAttributeName = new NSString(NSLigatureAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSLinkAttributeName();
public static final NSString NSLinkAttributeName = new NSString(NSLinkAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSModalPanelRunLoopMode();
public static final NSString NSModalPanelRunLoopMode = new NSString(NSModalPanelRunLoopMode());
/** @method flags=const */
public static final native long /*int*/ NSObliquenessAttributeName();
public static final NSString NSObliquenessAttributeName = new NSString(NSObliquenessAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSOutlineViewColumnDidMoveNotification();
public static final NSString NSOutlineViewColumnDidMoveNotification = new NSString(NSOutlineViewColumnDidMoveNotification());
/** @method flags=const */
public static final native long /*int*/ NSParagraphStyleAttributeName();
public static final NSString NSParagraphStyleAttributeName = new NSString(NSParagraphStyleAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSPrintAllPages();
public static final NSString NSPrintAllPages = new NSString(NSPrintAllPages());
/** @method flags=const */
public static final native long /*int*/ NSPrintCopies();
public static final NSString NSPrintCopies = new NSString(NSPrintCopies());
/** @method flags=const */
public static final native long /*int*/ NSPrintFirstPage();
public static final NSString NSPrintFirstPage = new NSString(NSPrintFirstPage());
/** @method flags=const */
public static final native long /*int*/ NSPrintJobDisposition();
public static final NSString NSPrintJobDisposition = new NSString(NSPrintJobDisposition());
/** @method flags=const */
public static final native long /*int*/ NSPrintLastPage();
public static final NSString NSPrintLastPage = new NSString(NSPrintLastPage());
/** @method flags=const */
public static final native long /*int*/ NSPrintMustCollate();
public static final NSString NSPrintMustCollate = new NSString(NSPrintMustCollate());
/** @method flags=const */
public static final native long /*int*/ NSPrintOrientation();
public static final NSString NSPrintOrientation = new NSString(NSPrintOrientation());
/** @method flags=const */
public static final native long /*int*/ NSPrintPreviewJob();
public static final NSString NSPrintPreviewJob = new NSString(NSPrintPreviewJob());
/** @method flags=const */
public static final native long /*int*/ NSPrintSaveJob();
public static final NSString NSPrintSaveJob = new NSString(NSPrintSaveJob());
/** @method flags=const */
public static final native long /*int*/ NSPrintSavePath();
public static final NSString NSPrintSavePath = new NSString(NSPrintSavePath());
/** @method flags=const */
public static final native long /*int*/ NSPrintScalingFactor();
public static final NSString NSPrintScalingFactor = new NSString(NSPrintScalingFactor());
/** @method flags=const */
public static final native long /*int*/ NSPrintSpoolJob();
public static final NSString NSPrintSpoolJob = new NSString(NSPrintSpoolJob());
/** @method flags=const */
public static final native long /*int*/ NSRTFPboardType();
public static final NSString NSRTFPboardType = new NSString(NSRTFPboardType());
/** @method flags=const */
public static final native long /*int*/ NSSpellingStateAttributeName();
public static final NSString NSSpellingStateAttributeName = new NSString(NSSpellingStateAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSStrikethroughColorAttributeName();
public static final NSString NSStrikethroughColorAttributeName = new NSString(NSStrikethroughColorAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSStrikethroughStyleAttributeName();
public static final NSString NSStrikethroughStyleAttributeName = new NSString(NSStrikethroughStyleAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSStringPboardType();
public static final NSString NSStringPboardType = new NSString(NSStringPboardType());
/** @method flags=const */
public static final native long /*int*/ NSStrokeWidthAttributeName();
public static final NSString NSStrokeWidthAttributeName = new NSString(NSStrokeWidthAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSSystemColorsDidChangeNotification();
public static final NSString NSSystemColorsDidChangeNotification = new NSString(NSSystemColorsDidChangeNotification());
/** @method flags=const */
public static final native long /*int*/ NSTIFFPboardType();
public static final NSString NSTIFFPboardType = new NSString(NSTIFFPboardType());
/** @method flags=const */
public static final native long /*int*/ NSTableViewColumnDidMoveNotification();
public static final NSString NSTableViewColumnDidMoveNotification = new NSString(NSTableViewColumnDidMoveNotification());
/** @method flags=const */
public static final native long /*int*/ NSToolbarCustomizeToolbarItemIdentifier();
public static final NSString NSToolbarCustomizeToolbarItemIdentifier = new NSString(NSToolbarCustomizeToolbarItemIdentifier());
/** @method flags=const */
public static final native long /*int*/ NSToolbarDidRemoveItemNotification();
public static final NSString NSToolbarDidRemoveItemNotification = new NSString(NSToolbarDidRemoveItemNotification());
/** @method flags=const */
public static final native long /*int*/ NSToolbarFlexibleSpaceItemIdentifier();
public static final NSString NSToolbarFlexibleSpaceItemIdentifier = new NSString(NSToolbarFlexibleSpaceItemIdentifier());
/** @method flags=const */
public static final native long /*int*/ NSToolbarPrintItemIdentifier();
public static final NSString NSToolbarPrintItemIdentifier = new NSString(NSToolbarPrintItemIdentifier());
/** @method flags=const */
public static final native long /*int*/ NSToolbarSeparatorItemIdentifier();
public static final NSString NSToolbarSeparatorItemIdentifier = new NSString(NSToolbarSeparatorItemIdentifier());
/** @method flags=const */
public static final native long /*int*/ NSToolbarShowColorsItemIdentifier();
public static final NSString NSToolbarShowColorsItemIdentifier = new NSString(NSToolbarShowColorsItemIdentifier());
/** @method flags=const */
public static final native long /*int*/ NSToolbarShowFontsItemIdentifier();
public static final NSString NSToolbarShowFontsItemIdentifier = new NSString(NSToolbarShowFontsItemIdentifier());
/** @method flags=const */
public static final native long /*int*/ NSToolbarSpaceItemIdentifier();
public static final NSString NSToolbarSpaceItemIdentifier = new NSString(NSToolbarSpaceItemIdentifier());
/** @method flags=const */
public static final native long /*int*/ NSToolbarWillAddItemNotification();
public static final NSString NSToolbarWillAddItemNotification = new NSString(NSToolbarWillAddItemNotification());
/** @method flags=const */
public static final native long /*int*/ NSURLPboardType();
public static final NSString NSURLPboardType = new NSString(NSURLPboardType());
/** @method flags=const */
public static final native long /*int*/ NSUnderlineColorAttributeName();
public static final NSString NSUnderlineColorAttributeName = new NSString(NSUnderlineColorAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSUnderlineStyleAttributeName();
public static final NSString NSUnderlineStyleAttributeName = new NSString(NSUnderlineStyleAttributeName());
/** @method flags=const */
public static final native long /*int*/ NSViewGlobalFrameDidChangeNotification();
public static final NSString NSViewGlobalFrameDidChangeNotification = new NSString(NSViewGlobalFrameDidChangeNotification());
/** @method flags=const */
public static final native long /*int*/ NSWindowDidBecomeKeyNotification();
public static final NSString NSWindowDidBecomeKeyNotification = new NSString(NSWindowDidBecomeKeyNotification());
/** @method flags=const */
public static final native long /*int*/ NSWindowDidDeminiaturizeNotification();
public static final NSString NSWindowDidDeminiaturizeNotification = new NSString(NSWindowDidDeminiaturizeNotification());
/** @method flags=const */
public static final native long /*int*/ NSWindowDidMiniaturizeNotification();
public static final NSString NSWindowDidMiniaturizeNotification = new NSString(NSWindowDidMiniaturizeNotification());
/** @method flags=const */
public static final native long /*int*/ NSWindowDidMoveNotification();
public static final NSString NSWindowDidMoveNotification = new NSString(NSWindowDidMoveNotification());
/** @method flags=const */
public static final native long /*int*/ NSWindowDidResignKeyNotification();
public static final NSString NSWindowDidResignKeyNotification = new NSString(NSWindowDidResignKeyNotification());
/** @method flags=const */
public static final native long /*int*/ NSWindowDidResizeNotification();
public static final NSString NSWindowDidResizeNotification = new NSString(NSWindowDidResizeNotification());
/** @method flags=const */
public static final native long /*int*/ NSWindowWillCloseNotification();
public static final NSString NSWindowWillCloseNotification = new NSString(NSWindowWillCloseNotification());
/** @method flags=const */
public static final native long /*int*/ kCFAllocatorDefault();
/** @method flags=const */
public static final native long /*int*/ kCFRunLoopCommonModes();
/** @method flags=const */
public static final native long /*int*/ kCTFontAttributeName();
/** @method flags=const */
public static final native long /*int*/ kCTForegroundColorAttributeName();
/** @method flags=const */
public static final native long /*int*/ kCTParagraphStyleAttributeName();
/** @method flags=const */
public static final native long /*int*/ NSDefaultRunLoopMode();
public static final NSString NSDefaultRunLoopMode = new NSString(NSDefaultRunLoopMode());
/** @method flags=const */
public static final native long /*int*/ NSErrorFailingURLStringKey();
public static final NSString NSErrorFailingURLStringKey = new NSString(NSErrorFailingURLStringKey());
/** @method flags=const */
public static final native long /*int*/ NSLocaleLanguageCode();
public static final NSString NSLocaleLanguageCode = new NSString(NSLocaleLanguageCode());

/** Functions */

/**
 * @param action cast=(NSString*)
 */
public static final native long /*int*/ NSAccessibilityActionDescription(long /*int*/ action);
/**
 * @param element cast=(id)
 * @param notification cast=(NSString*)
 */
public static final native void NSAccessibilityPostNotification(long /*int*/ element, long /*int*/ notification);
/**
 * @param element cast=(id)
 * @param attribute cast=(NSString*)
 * @param value cast=(id)
 */
public static final native void NSAccessibilityRaiseBadArgumentException(long /*int*/ element, long /*int*/ attribute, long /*int*/ value);
/**
 * @param role cast=(NSString*)
 * @param subrole cast=(NSString*)
 */
public static final native long /*int*/ NSAccessibilityRoleDescription(long /*int*/ role, long /*int*/ subrole);
/**
 * @param element cast=(id)
 */
public static final native long /*int*/ NSAccessibilityRoleDescriptionForUIElement(long /*int*/ element);
/**
 * @param element cast=(id)
 */
public static final native long /*int*/ NSAccessibilityUnignoredAncestor(long /*int*/ element);
/**
 * @param originalChildren cast=(NSArray*)
 */
public static final native long /*int*/ NSAccessibilityUnignoredChildren(long /*int*/ originalChildren);
/**
 * @param originalChild cast=(id)
 */
public static final native long /*int*/ NSAccessibilityUnignoredChildrenForOnlyChild(long /*int*/ originalChild);
/**
 * @param element cast=(id)
 */
public static final native long /*int*/ NSAccessibilityUnignoredDescendant(long /*int*/ element);
public static final native void NSBeep();
/**
 * @param depth cast=(NSWindowDepth)
 */
public static final native long /*int*/ NSBitsPerPixelFromDepth(int depth);
/**
 * @param srcGState cast=(NSInteger)
 * @param srcRect flags=struct
 * @param destPoint flags=struct
 */
public static final native void NSCopyBits(long /*int*/ srcGState, NSRect srcRect, NSPoint destPoint);
/**
 * @param count cast=(NSInteger*)
 */
public static final native void NSCountWindows(long[] /*int[]*/ count);
/**
 * @param colorSpaceName cast=(NSString*)
 */
public static final native long /*int*/ NSNumberOfColorComponents(long /*int*/ colorSpaceName);
/**
 * @param aRect flags=struct
 * @param op cast=(NSCompositingOperation)
 */
public static final native void NSRectFillUsingOperation(NSRect aRect, long /*int*/ op);
/**
 * @param size cast=(NSInteger)
 * @param list cast=(NSInteger*)
 */
public static final native void NSWindowList(long /*int*/ size, long[] /*int[]*/ list);
/**
 * @param alloc cast=(CFAllocatorRef)
 * @param str cast=(CFStringRef)
 * @param attributes cast=(CFDictionaryRef)
 */
public static final native long /*int*/ CFAttributedStringCreate(long /*int*/ alloc, long /*int*/ str, long /*int*/ attributes);
/**
 * @param theData cast=(CFDataRef)
 */
public static final native long /*int*/ CFDataGetBytePtr(long /*int*/ theData);
/**
 * @param theData cast=(CFDataRef)
 */
public static final native long /*int*/ CFDataGetLength(long /*int*/ theData);
/**
 * @param theDict cast=(CFMutableDictionaryRef)
 * @param key cast=(void*)
 * @param value cast=(void*)
 */
public static final native void CFDictionaryAddValue(long /*int*/ theDict, long /*int*/ key, long /*int*/ value);
/**
 * @param allocator cast=(CFAllocatorRef)
 * @param capacity cast=(CFIndex)
 * @param keyCallBacks cast=(CFDictionaryKeyCallBacks*)
 * @param valueCallBacks cast=(CFDictionaryValueCallBacks*)
 */
public static final native long /*int*/ CFDictionaryCreateMutable(long /*int*/ allocator, long /*int*/ capacity, long /*int*/ keyCallBacks, long /*int*/ valueCallBacks);
/**
 * @param cf cast=(CFTypeRef)
 */
public static final native void CFRelease(long /*int*/ cf);
/**
 * @param rl cast=(CFRunLoopRef)
 * @param observer cast=(CFRunLoopObserverRef)
 * @param mode cast=(CFStringRef)
 */
public static final native void CFRunLoopAddObserver(long /*int*/ rl, long /*int*/ observer, long /*int*/ mode);
public static final native long /*int*/ CFRunLoopGetCurrent();
/**
 * @param allocator cast=(CFAllocatorRef)
 * @param activities cast=(CFOptionFlags)
 * @param repeats cast=(Boolean)
 * @param order cast=(CFIndex)
 * @param callout cast=(CFRunLoopObserverCallBack)
 * @param context cast=(CFRunLoopObserverContext*)
 */
public static final native long /*int*/ CFRunLoopObserverCreate(long /*int*/ allocator, long /*int*/ activities, boolean repeats, long /*int*/ order, long /*int*/ callout, long /*int*/ context);
/**
 * @param observer cast=(CFRunLoopObserverRef)
 */
public static final native void CFRunLoopObserverInvalidate(long /*int*/ observer);
/**
 * @param mode cast=(CFStringRef)
 * @param seconds cast=(CFTimeInterval)
 * @param returnAfterSourceHandled cast=(Boolean)
 */
public static final native int CFRunLoopRunInMode(long /*int*/ mode, double seconds, boolean returnAfterSourceHandled);
/**
 * @param rl cast=(CFRunLoopRef)
 */
public static final native void CFRunLoopStop(long /*int*/ rl);
/**
 * @param alloc cast=(CFAllocatorRef)
 * @param chars cast=(UniChar*)
 * @param numChars cast=(CFIndex)
 */
public static final native long /*int*/ CFStringCreateWithCharacters(long /*int*/ alloc, char[] chars, long /*int*/ numChars);
/**
 * @param allocator cast=(CFAllocatorRef)
 * @param fsRef cast=(struct FSRef*)
 */
public static final native long /*int*/ CFURLCreateFromFSRef(long /*int*/ allocator, byte[] fsRef);
/**
 * @param allocator cast=(CFAllocatorRef)
 * @param originalString cast=(CFStringRef)
 * @param charactersToLeaveUnescaped cast=(CFStringRef)
 * @param legalURLCharactersToBeEscaped cast=(CFStringRef)
 * @param encoding cast=(CFStringEncoding)
 */
public static final native long /*int*/ CFURLCreateStringByAddingPercentEscapes(long /*int*/ allocator, long /*int*/ originalString, long /*int*/ charactersToLeaveUnescaped, long /*int*/ legalURLCharactersToBeEscaped, int encoding);
/**
 * @param data cast=(void*)
 * @param width cast=(size_t)
 * @param height cast=(size_t)
 * @param bitsPerComponent cast=(size_t)
 * @param bytesPerRow cast=(size_t)
 * @param colorspace cast=(CGColorSpaceRef)
 * @param bitmapInfo cast=(CGBitmapInfo)
 */
public static final native long /*int*/ CGBitmapContextCreate(long /*int*/ data, long /*int*/ width, long /*int*/ height, long /*int*/ bitsPerComponent, long /*int*/ bytesPerRow, long /*int*/ colorspace, int bitmapInfo);
/**
 * @param c cast=(CGContextRef)
 */
public static final native long /*int*/ CGBitmapContextCreateImage(long /*int*/ c);
/**
 * @param c cast=(CGContextRef)
 */
public static final native long /*int*/ CGBitmapContextGetData(long /*int*/ c);
/**
 * @param space cast=(CGColorSpaceRef)
 * @param components cast=(CGFloat*)
 */
public static final native long /*int*/ CGColorCreate(long /*int*/ space, double[] /*float[]*/ components);
/**
 * @param color cast=(CGColorRef)
 */
public static final native void CGColorRelease(long /*int*/ color);
public static final native long /*int*/ CGColorSpaceCreateDeviceRGB();
/**
 * @param space cast=(CGColorSpaceRef)
 */
public static final native void CGColorSpaceRelease(long /*int*/ space);
/**
 * @param context cast=(CGContextRef)
 * @param path cast=(CGPathRef)
 */
public static final native void CGContextAddPath(long /*int*/ context, long /*int*/ path);
/**
 * @param context cast=(CGContextRef)
 * @param rect flags=struct
 * @param auxiliaryInfo cast=(CFDictionaryRef)
 */
public static final native void CGContextBeginTransparencyLayerWithRect(long /*int*/ context, CGRect rect, long /*int*/ auxiliaryInfo);
/**
 * @param c cast=(CGContextRef)
 * @param rect flags=struct
 * @param image cast=(CGImageRef)
 */
public static final native void CGContextDrawImage(long /*int*/ c, CGRect rect, long /*int*/ image);
/**
 * @param context cast=(CGContextRef)
 */
public static final native void CGContextEndTransparencyLayer(long /*int*/ context);
/**
 * @param c cast=(CGContextRef)
 * @param rect flags=struct
 */
public static final native void CGContextFillRect(long /*int*/ c, CGRect rect);
/**
 * @param c cast=(CGContextRef)
 */
public static final native void CGContextRelease(long /*int*/ c);
/**
 * @param c cast=(CGContextRef)
 */
public static final native void CGContextReplacePathWithStrokedPath(long /*int*/ c);
/**
 * @param c cast=(CGContextRef)
 */
public static final native void CGContextRestoreGState(long /*int*/ c);
/**
 * @param c cast=(CGContextRef)
 */
public static final native void CGContextSaveGState(long /*int*/ c);
/**
 * @param c cast=(CGContextRef)
 * @param sx cast=(CGFloat)
 * @param sy cast=(CGFloat)
 */
public static final native void CGContextScaleCTM(long /*int*/ c, double /*float*/ sx, double /*float*/ sy);
/**
 * @param context cast=(CGContextRef)
 * @param mode cast=(CGBlendMode)
 */
public static final native void CGContextSetBlendMode(long /*int*/ context, int mode);
/**
 * @param c cast=(CGContextRef)
 * @param components cast=(CGFloat*)
 */
public static final native void CGContextSetFillColor(long /*int*/ c, double[] /*float[]*/ components);
/**
 * @param c cast=(CGContextRef)
 * @param colorspace cast=(CGColorSpaceRef)
 */
public static final native void CGContextSetFillColorSpace(long /*int*/ c, long /*int*/ colorspace);
/**
 * @param c cast=(CGContextRef)
 * @param cap cast=(CGLineCap)
 */
public static final native void CGContextSetLineCap(long /*int*/ c, int cap);
/**
 * @param c cast=(CGContextRef)
 * @param phase cast=(CGFloat)
 * @param lengths cast=(CGFloat*)
 * @param count cast=(size_t)
 */
public static final native void CGContextSetLineDash(long /*int*/ c, double /*float*/ phase, float[] lengths, long /*int*/ count);
/**
 * @param c cast=(CGContextRef)
 * @param join cast=(CGLineJoin)
 */
public static final native void CGContextSetLineJoin(long /*int*/ c, int join);
/**
 * @param c cast=(CGContextRef)
 * @param width cast=(CGFloat)
 */
public static final native void CGContextSetLineWidth(long /*int*/ c, double /*float*/ width);
/**
 * @param c cast=(CGContextRef)
 * @param limit cast=(CGFloat)
 */
public static final native void CGContextSetMiterLimit(long /*int*/ c, double /*float*/ limit);
/**
 * @param c cast=(CGContextRef)
 * @param shouldAntialias cast=(_Bool)
 */
public static final native void CGContextSetShouldAntialias(long /*int*/ c, boolean shouldAntialias);
/**
 * @param c cast=(CGContextRef)
 * @param mode cast=(CGTextDrawingMode)
 */
public static final native void CGContextSetTextDrawingMode(long /*int*/ c, int mode);
/**
 * @param c cast=(CGContextRef)
 * @param t flags=struct
 */
public static final native void CGContextSetTextMatrix(long /*int*/ c, CGAffineTransform t);
/**
 * @param c cast=(CGContextRef)
 * @param x cast=(CGFloat)
 * @param y cast=(CGFloat)
 */
public static final native void CGContextSetTextPosition(long /*int*/ c, double /*float*/ x, double /*float*/ y);
/**
 * @param c cast=(CGContextRef)
 */
public static final native void CGContextStrokePath(long /*int*/ c);
/**
 * @param c cast=(CGContextRef)
 * @param tx cast=(CGFloat)
 * @param ty cast=(CGFloat)
 */
public static final native void CGContextTranslateCTM(long /*int*/ c, double /*float*/ tx, double /*float*/ ty);
/**
 * @param info cast=(void*)
 * @param data cast=(void*)
 * @param size cast=(size_t)
 * @param releaseData cast=(CGDataProviderReleaseDataCallback)
 */
public static final native long /*int*/ CGDataProviderCreateWithData(long /*int*/ info, long /*int*/ data, long /*int*/ size, long /*int*/ releaseData);
/**
 * @param provider cast=(CGDataProviderRef)
 */
public static final native void CGDataProviderRelease(long /*int*/ provider);
/**
 * @param display cast=(CGDirectDisplayID)
 */
public static final native long /*int*/ CGDisplayPixelsHigh(int display);
/**
 * @param display cast=(CGDirectDisplayID)
 */
public static final native long /*int*/ CGDisplayPixelsWide(int display);
/**
 * @param source cast=(CGEventSourceRef)
 * @param virtualKey cast=(CGKeyCode)
 * @param keyDown cast=(_Bool)
 */
public static final native long /*int*/ CGEventCreateKeyboardEvent(long /*int*/ source, short virtualKey, boolean keyDown);
/**
 * @param source cast=(CGEventSourceRef)
 * @param mouseType cast=(CGEventType)
 * @param mouseCursorPosition flags=struct
 * @param mouseButton cast=(CGMouseButton)
 */
public static final native long /*int*/ CGEventCreateMouseEvent(long /*int*/ source, int mouseType, CGPoint mouseCursorPosition, int mouseButton);
/**
 * @param source cast=(CGEventSourceRef)
 * @param units cast=(CGScrollEventUnit)
 * @param wheelCount cast=(CGWheelCount)
 * @param wheel1 cast=(int32_t)
 */
public static final native long /*int*/ CGEventCreateScrollWheelEvent(long /*int*/ source, int units, int wheelCount, int wheel1);
/**
 * @param event cast=(CGEventRef)
 * @param field cast=(CGEventField)
 */
public static final native long CGEventGetIntegerValueField(long /*int*/ event, int field);
/**
 * @param tap cast=(CGEventTapLocation)
 * @param event cast=(CGEventRef)
 */
public static final native void CGEventPost(int tap, long /*int*/ event);
/**
 * @param sourceState cast=(CGEventSourceStateID)
 */
public static final native long /*int*/ CGEventSourceCreate(int sourceState);
/**
 * @param rect flags=struct
 * @param maxDisplays cast=(CGDisplayCount)
 * @param dspys cast=(CGDirectDisplayID*)
 * @param dspyCnt cast=(CGDisplayCount*)
 */
public static final native int CGGetDisplaysWithRect(CGRect rect, int maxDisplays, long /*int*/ dspys, long /*int*/ dspyCnt);
/**
 * @param width cast=(size_t)
 * @param height cast=(size_t)
 * @param bitsPerComponent cast=(size_t)
 * @param bitsPerPixel cast=(size_t)
 * @param bytesPerRow cast=(size_t)
 * @param colorspace cast=(CGColorSpaceRef)
 * @param bitmapInfo cast=(CGBitmapInfo)
 * @param provider cast=(CGDataProviderRef)
 * @param decode cast=(CGFloat*)
 * @param shouldInterpolate cast=(_Bool)
 * @param intent cast=(CGColorRenderingIntent)
 */
public static final native long /*int*/ CGImageCreate(long /*int*/ width, long /*int*/ height, long /*int*/ bitsPerComponent, long /*int*/ bitsPerPixel, long /*int*/ bytesPerRow, long /*int*/ colorspace, int bitmapInfo, long /*int*/ provider, long /*int*/ decode, boolean shouldInterpolate, int intent);
/**
 * @param image cast=(CGImageRef)
 */
public static final native long /*int*/ CGImageGetHeight(long /*int*/ image);
/**
 * @param image cast=(CGImageRef)
 */
public static final native long /*int*/ CGImageGetWidth(long /*int*/ image);
/**
 * @param image cast=(CGImageRef)
 */
public static final native void CGImageRelease(long /*int*/ image);
/**
 * @param path cast=(CGMutablePathRef)
 * @param m cast=(CGAffineTransform*)
 * @param cp1x cast=(CGFloat)
 * @param cp1y cast=(CGFloat)
 * @param cp2x cast=(CGFloat)
 * @param cp2y cast=(CGFloat)
 * @param x cast=(CGFloat)
 * @param y cast=(CGFloat)
 */
public static final native void CGPathAddCurveToPoint(long /*int*/ path, long /*int*/ m, double /*float*/ cp1x, double /*float*/ cp1y, double /*float*/ cp2x, double /*float*/ cp2y, double /*float*/ x, double /*float*/ y);
/**
 * @param path cast=(CGMutablePathRef)
 * @param m cast=(CGAffineTransform*)
 * @param x cast=(CGFloat)
 * @param y cast=(CGFloat)
 */
public static final native void CGPathAddLineToPoint(long /*int*/ path, long /*int*/ m, double /*float*/ x, double /*float*/ y);
/**
 * @param path cast=(CGMutablePathRef)
 * @param m cast=(CGAffineTransform*)
 * @param rect flags=struct
 */
public static final native void CGPathAddRect(long /*int*/ path, long /*int*/ m, CGRect rect);
/**
 * @param path cast=(CGPathRef)
 * @param info cast=(void*)
 * @param function cast=(CGPathApplierFunction)
 */
public static final native void CGPathApply(long /*int*/ path, long /*int*/ info, long /*int*/ function);
/**
 * @param path cast=(CGMutablePathRef)
 */
public static final native void CGPathCloseSubpath(long /*int*/ path);
/**
 * @param path cast=(CGPathRef)
 */
public static final native long /*int*/ CGPathCreateCopy(long /*int*/ path);
public static final native long /*int*/ CGPathCreateMutable();
/**
 * @param path cast=(CGMutablePathRef)
 * @param m cast=(CGAffineTransform*)
 * @param x cast=(CGFloat)
 * @param y cast=(CGFloat)
 */
public static final native void CGPathMoveToPoint(long /*int*/ path, long /*int*/ m, double /*float*/ x, double /*float*/ y);
/**
 * @param path cast=(CGPathRef)
 */
public static final native void CGPathRelease(long /*int*/ path);
/**
 * @param keyChar cast=(CGCharCode)
 * @param virtualKey cast=(CGKeyCode)
 * @param keyDown cast=(boolean_t)
 */
public static final native int CGPostKeyboardEvent(short keyChar, short virtualKey, boolean keyDown);
/**
 * @param filter cast=(CGEventFilterMask)
 * @param state cast=(CGEventSuppressionState)
 */
public static final native int CGSetLocalEventsFilterDuringSuppressionState(int filter, int state);
/**
 * @param seconds cast=(CFTimeInterval)
 */
public static final native int CGSetLocalEventsSuppressionInterval(double seconds);
/**
 * @param newCursorPosition flags=struct
 */
public static final native int CGWarpMouseCursorPosition(CGPoint newCursorPosition);
/**
 * @param font cast=(CTFontRef)
 */
public static final native double /*float*/ CTFontGetAscent(long /*int*/ font);
/**
 * @param font cast=(CTFontRef)
 */
public static final native double /*float*/ CTFontGetDescent(long /*int*/ font);
/**
 * @param font cast=(CTFontRef)
 */
public static final native double /*float*/ CTFontGetLeading(long /*int*/ font);
/**
 * @param string cast=(CFAttributedStringRef)
 */
public static final native long /*int*/ CTLineCreateWithAttributedString(long /*int*/ string);
/**
 * @param line cast=(CTLineRef)
 * @param context cast=(CGContextRef)
 */
public static final native void CTLineDraw(long /*int*/ line, long /*int*/ context);
/**
 * @param line cast=(CTLineRef)
 * @param ascent cast=(CGFloat*)
 * @param descent cast=(CGFloat*)
 * @param leading cast=(CGFloat*)
 */
public static final native double CTLineGetTypographicBounds(long /*int*/ line, double[] /*float[]*/ ascent, double[] /*float[]*/ descent, double[] /*float[]*/ leading);
/**
 * @param settings cast=(CTParagraphStyleSetting*)
 * @param settingCount cast=(CFIndex)
 */
public static final native long /*int*/ CTParagraphStyleCreate(long /*int*/ settings, long /*int*/ settingCount);
/**
 * @param typesetter cast=(CTTypesetterRef)
 * @param stringRange flags=struct
 */
public static final native long /*int*/ CTTypesetterCreateLine(long /*int*/ typesetter, CFRange stringRange);
/**
 * @param string cast=(CFAttributedStringRef)
 */
public static final native long /*int*/ CTTypesetterCreateWithAttributedString(long /*int*/ string);
/**
 * @param typesetter cast=(CTTypesetterRef)
 * @param startIndex cast=(CFIndex)
 * @param width cast=(double)
 */
public static final native long /*int*/ CTTypesetterSuggestLineBreak(long /*int*/ typesetter, long /*int*/ startIndex, double width);
/**
 * @param aRect flags=struct
 * @param bRect flags=struct
 */
public static final native boolean NSEqualRects(NSRect aRect, NSRect bRect);
/**
 * @param hfsFileTypeCode cast=(OSType)
 */
public static final native long /*int*/ NSFileTypeForHFSTypeCode(int hfsFileTypeCode);
/**
 * @param typePtr cast=(char*)
 * @param sizep cast=(NSUInteger*)
 * @param alignp cast=(NSUInteger*)
 */
public static final native long /*int*/ NSGetSizeAndAlignment(long /*int*/ typePtr, long[] /*int[]*/ sizep, long[] /*int[]*/ alignp);
/**
 * @param aPoint flags=struct
 * @param aRect flags=struct
 */
public static final native boolean NSPointInRect(NSPoint aPoint, NSRect aRect);
/**
 * @param directory cast=(NSSearchPathDirectory)
 * @param domainMask cast=(NSSearchPathDomainMask)
 * @param expandTilde cast=(BOOL)
 */
public static final native long /*int*/ NSSearchPathForDirectoriesInDomains(long /*int*/ directory, long /*int*/ domainMask, boolean expandTilde);
public static final native long /*int*/ NSTemporaryDirectory();

/** Super Sends */

/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native boolean objc_msgSendSuper_bool(objc_super superId, long /*int*/ sel, NSRange arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native boolean objc_msgSendSuper_bool(objc_super superId, long /*int*/ sel, long /*int*/ arg0, NSPoint arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, NSPoint arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, NSRect arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, NSRect arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, NSSize arg0);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, boolean arg0);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, boolean arg0, NSRect arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, long /*int*/ arg0);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, long /*int*/ arg0, NSPoint arg1);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, long /*int*/ arg0, NSPoint arg1, long /*int*/ arg2);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, long /*int*/ arg0, NSRect arg1, long /*int*/ arg2);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, long /*int*/ arg0, boolean arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, boolean arg3);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSendSuper(objc_super superId, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSendSuper_stret(NSRect result, objc_super superId, long /*int*/ sel, NSRect arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSendSuper_stret(NSRect result, objc_super superId, long /*int*/ sel, NSRect arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native void objc_msgSendSuper_stret(NSRect result, objc_super superId, long /*int*/ sel, long /*int*/ arg0);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native void objc_msgSendSuper_stret(NSRect result, objc_super superId, long /*int*/ sel, long /*int*/ arg0, NSRect arg1, long /*int*/ arg2);
/** @method flags=cast */
public static final native void objc_msgSendSuper_stret(NSSize result, objc_super superId, long /*int*/ sel);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSendSuper_stret(NSSize result, objc_super superId, long /*int*/ sel, NSRect arg0);
/** @method flags=cast */
public static final native void objc_msgSendSuper_stret(NSSize result, objc_super superId, long /*int*/ sel, boolean arg0);

/** Sends */

/** @method flags=cast */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, NSPoint arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 * @param arg1 flags=struct
 */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, NSPoint arg0, NSRect arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, NSRange arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, NSRect arg0);
/** @method flags=cast */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSPoint arg1);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSSize arg1, boolean arg2);
/** @method flags=cast */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2);
/** @method flags=cast */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3);
/** @method flags=cast */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3, long /*int*/ arg4);
/** @method flags=cast */
public static final native boolean objc_msgSend_bool(long /*int*/ id, long /*int*/ sel, short arg0);
/** @method flags=cast */
public static final native double objc_msgSend_fpret(long /*int*/ id, long /*int*/ sel);
/** @method flags=cast */
public static final native double objc_msgSend_fpret(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0);
/** @method flags=cast */
public static final native double objc_msgSend_fpret(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native float objc_msgSend_floatret(long /*int*/ id, long /*int*/ sel);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSAffineTransformStruct arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSPoint arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 * @param arg1 flags=struct
 * @param arg2 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSPoint arg0, NSPoint arg1, NSPoint arg2);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSPoint arg0, NSPoint arg1, long /*int*/ arg2);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSPoint arg0, NSRect arg1, long /*int*/ arg2, double /*float*/ arg3);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSPoint arg0, double /*float*/ arg1, double /*float*/ arg2, double /*float*/ arg3);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSPoint arg0, double /*float*/ arg1, double /*float*/ arg2, double /*float*/ arg3, boolean arg4);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSPoint arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSPoint arg0, long /*int*/ arg1, double[] /*float[]*/ arg2);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRange arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRange arg0, NSPoint arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRange arg0, NSRange arg1, long /*int*/ arg2, long[] /*int[]*/ arg3);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRange arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRange arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3, long /*int*/ arg4, byte[] arg5);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, NSPoint arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 * @param arg1 flags=struct
 * @param arg2 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, NSRange arg1, NSRect arg2);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, NSRect arg1, long /*int*/ arg2, double /*float*/ arg3);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, boolean arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, boolean arg1, boolean arg2);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, double /*float*/ arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, double /*float*/ arg1, double /*float*/ arg2);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, long /*int*/ arg1, boolean arg2, long /*int*/ arg3);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, long /*int*/ arg1, long /*int*/ arg2);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, long /*int*/ arg1, long /*int*/ arg2, boolean arg3);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, long /*int*/ arg1, long /*int*/ arg2, boolean arg3, long /*int*/ arg4);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSRect arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, NSSize arg0);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, boolean arg0);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, boolean arg0, NSRect arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, boolean arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, byte[] arg0);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, byte[] arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, char[] arg0);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, char[] arg0, NSRange arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, char[] arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, double arg0);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, double arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3, boolean arg4);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, double /*float*/ arg0, double /*float*/ arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, double /*float*/ arg0, double /*float*/ arg1, double /*float*/ arg2, double /*float*/ arg3);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, double /*float*/ arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, double[] /*float[]*/ arg0);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, double[] /*float[]*/ arg0, long /*int*/ arg1, double /*float*/ arg2);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSPoint arg1);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 * @param arg2 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSPoint arg1, NSSize arg2, long /*int*/ arg3, long /*int*/ arg4, long /*int*/ arg5, boolean arg6);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSPoint arg1, long /*int*/ arg2);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSPoint arg1, long /*int*/ arg2, double arg3, long /*int*/ arg4, long /*int*/ arg5, long /*int*/ arg6, long /*int*/ arg7, long /*int*/ arg8);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSPoint arg1, long /*int*/ arg2, double arg3, long /*int*/ arg4, long /*int*/ arg5, short arg6, long /*int*/ arg7, long /*int*/ arg8);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSRange arg1);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSRect arg1, long /*int*/ arg2);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, boolean arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, double /*float*/ arg1);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg2 flags=struct
 */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, NSRange arg2);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, boolean arg2);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, double arg2, long /*int*/ arg3);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, boolean arg3);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, double /*float*/ arg3);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3, long /*int*/ arg4);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3, long /*int*/ arg4, boolean arg5, boolean arg6, long /*int*/ arg7, long /*int*/ arg8, long /*int*/ arg9);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3, long /*int*/ arg4, boolean arg5, boolean arg6, long /*int*/ arg7, long /*int*/ arg8, long /*int*/ arg9, long /*int*/ arg10);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3, long /*int*/ arg4, long /*int*/ arg5);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, long /*int*/ arg2, long /*int*/ arg3, long /*int*/ arg4, long /*int*/ arg5, long /*int*/ arg6);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long[] /*int[]*/ arg0);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, long[] /*int[]*/ arg0, int arg1, int arg2);
/** @method flags=cast */
public static final native long /*int*/ objc_msgSend(long /*int*/ id, long /*int*/ sel, int[] arg0, int arg1);
/** @method flags=cast */
public static final native int objc_msgSend(int id, int sel, float arg0);
/** @method flags=cast */
public static final native long objc_msgSend(long id, long sel, int arg0);
/** @method flags=cast */
public static final native long objc_msgSend(long id, long sel, int[] arg0);
/** @method flags=cast */
public static final native long objc_msgSend(long id, long sel, long[] arg0, long arg1, long arg2);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSAffineTransformStruct result, long /*int*/ id, long /*int*/ sel);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSPoint result, long /*int*/ id, long /*int*/ sel);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSPoint result, long /*int*/ id, long /*int*/ sel, NSPoint arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSPoint result, long /*int*/ id, long /*int*/ sel, NSPoint arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSPoint result, long /*int*/ id, long /*int*/ sel, long /*int*/ arg0);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSRange result, long /*int*/ id, long /*int*/ sel);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSRange result, long /*int*/ id, long /*int*/ sel, NSRange arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSRange result, long /*int*/ id, long /*int*/ sel, NSRect arg0);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSRange result, long /*int*/ id, long /*int*/ sel, long /*int*/ arg0);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSRect result, long /*int*/ id, long /*int*/ sel);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSRect result, long /*int*/ id, long /*int*/ sel, NSRange arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSRect result, long /*int*/ id, long /*int*/ sel, NSRect arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSRect result, long /*int*/ id, long /*int*/ sel, NSRect arg0, long /*int*/ arg1);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSRect result, long /*int*/ id, long /*int*/ sel, NSSize arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSRect result, long /*int*/ id, long /*int*/ sel, long /*int*/ arg0);
/**
 * @method flags=cast
 * @param arg1 flags=struct
 */
public static final native void objc_msgSend_stret(NSRect result, long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, NSRect arg1, long /*int*/ arg2);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSRect result, long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSRect result, long /*int*/ id, long /*int*/ sel, long /*int*/ arg0, long /*int*/ arg1, boolean arg2);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSSize result, long /*int*/ id, long /*int*/ sel);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSSize result, long /*int*/ id, long /*int*/ sel, NSRect arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSSize result, long /*int*/ id, long /*int*/ sel, NSSize arg0);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSSize result, long /*int*/ id, long /*int*/ sel, NSSize arg0, boolean arg1, boolean arg2, long /*int*/ arg3);
/**
 * @method flags=cast
 * @param arg0 flags=struct
 */
public static final native void objc_msgSend_stret(NSSize result, long /*int*/ id, long /*int*/ sel, NSSize arg0, long /*int*/ arg1);
/** @method flags=cast */
public static final native void objc_msgSend_stret(NSSize result, long /*int*/ id, long /*int*/ sel, boolean arg0);

/** Sizeof natives */
public static final native int CFRange_sizeof();
public static final native int CGAffineTransform_sizeof();
public static final native int CGPathElement_sizeof();
public static final native int CGPoint_sizeof();
public static final native int CGRect_sizeof();
public static final native int CGSize_sizeof();
public static final native int CTParagraphStyleSetting_sizeof();
public static final native int NSAffineTransformStruct_sizeof();
public static final native int NSPoint_sizeof();
public static final native int NSRange_sizeof();
public static final native int NSRect_sizeof();
public static final native int NSSize_sizeof();

/** Memmove natives */

/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, CFRange src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(CFRange dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, CGAffineTransform src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(CGAffineTransform dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, CGPathElement src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(CGPathElement dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, CGPoint src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(CGPoint dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, CGRect src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(CGRect dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, CGSize src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(CGSize dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, CTParagraphStyleSetting src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(CTParagraphStyleSetting dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, NSAffineTransformStruct src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(NSAffineTransformStruct dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, NSPoint src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(NSPoint dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, NSRange src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(NSRange dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, NSRect src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(NSRect dest, long /*int*/ src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(long /*int*/ dest, NSSize src, long /*int*/ size);
/**
 * @param dest cast=(void *),flags=no_in critical
 * @param src cast=(void *),flags=critical
 */
public static final native void memmove(NSSize dest, long /*int*/ src, long /*int*/ size);

/** This section is auto generated */
}
