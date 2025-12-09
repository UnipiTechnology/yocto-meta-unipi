require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

SRCREV = "6d41f0a39d6423c8e57e92ebbe9f8c0333a63f72"
SRC_URI:append = " \
 file://0001-Add-driver-for-i2c-bcm2835.patch \
 file://0002-Add-support-for-bootcount-in-ds1307-rtc-chip.patch \
 file://0003-Add-support-for-udma-ranges-as-override-of-dma-range.patch \
 file://0004-Add-support-for-RS485-console.patch \
 file://0005-Add-new-boot-methods-altboot-and-tryboot.patch \
 file://0006-Add-Unipi-board-files.patch \
"

DEPENDS += "bc-native dtc-native python3-pyelftools-native"
COMPATIBLE_MACHINE = "unipi-edge"
RDEPENDS:${PN} += "u-boot-default-script"
