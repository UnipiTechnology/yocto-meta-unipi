LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=fed54355545ffd980b814dab4a3b312c"

require unipi-tools.inc

SRCREV = "8c16ca6ad7be92baeaac73e703f3e98b987e1100"
SRC_URI = "git://github.com/UniPiTechnology/unipi-tools.git;protocol=https;branch=main"

DEPENDS += "i2c-tools"

