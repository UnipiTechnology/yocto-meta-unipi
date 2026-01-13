FILESEXTRAPATHS:prepend := "${THISDIR}/base-files:"

SRC_URI:append:unipi-edge = " file://fstab"

do_install:append () {
	install -m 0755 -d ${D}/boot/firmware
}
