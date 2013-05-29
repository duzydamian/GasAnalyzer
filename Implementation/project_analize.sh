#!/bin/bash

lines=`cat \`find | grep "\.java"\` | grep "" -c`
classes=`find | grep "\.java"  -c`

echo "liczba linii: "$lines
echo "liczba klas: "$classes

