#!/bin/sh
# AUTO-GENERATED FILE, DO NOT EDIT!
if [ -f $1.org ]; then
  sed -e 's!^C:/cygwin/lib!/usr/lib!ig;s! C:/cygwin/lib! /usr/lib!ig;s!^C:/cygwin/bin!/usr/bin!ig;s! C:/cygwin/bin! /usr/bin!ig;s!^C:/cygwin/!/!ig;s! C:/cygwin/! /!ig;s!^Z:!/cygdrive/z!ig;s! Z:! /cygdrive/z!ig;s!^Y:!/cygdrive/y!ig;s! Y:! /cygdrive/y!ig;s!^N:!/cygdrive/n!ig;s! N:! /cygdrive/n!ig;s!^G:!/cygdrive/g!ig;s! G:! /cygdrive/g!ig;s!^E:!/cygdrive/e!ig;s! E:! /cygdrive/e!ig;s!^C:!/cygdrive/c!ig;s! C:! /cygdrive/c!ig;' $1.org > $1 && rm -f $1.org
fi
