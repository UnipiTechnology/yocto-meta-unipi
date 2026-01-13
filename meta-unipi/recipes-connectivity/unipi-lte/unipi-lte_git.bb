SUMMARY = "Unipi LTE modem manager"
DESCRIPTION = "Python utility for starting and monitoring Unipi modems."
SECTION = "net"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRCREV = "3b13489d436bc174679d119a9e3c38f269797070"
PV = "0.72"
#PE = "1"

SRC_URI = "git://git.unipi.technology/UniPi/unipi-lte;branch=main-trixie;protocol=https"

S = "${WORKDIR}/git"

inherit systemd

do_configure() {
    :
}

do_compile() {
    :
}

do_install() {
    install -m 755 -d ${D}${sbindir}
    install -m 755 ${S}/files/usr/sbin/unipi-lte.py ${D}${sbindir}
    install -m 755 -d ${D}${sysconfdir}/unipi
    install -m 600 ${S}/files/etc/unipi/lte.conf ${D}${sysconfdir}/unipi
    install -m 755 -d ${D}${libdir}/tmpfiles.d
    install -m 644 ${S}/files/usr/lib/tmpfiles.d/lte-virtual-regs.conf ${D}${libdir}/tmpfiles.d
    install -m 755 -d ${D}${systemd_unitdir}/system
    install -m 644 ${S}/debian/unipi-lte.service ${D}${systemd_unitdir}/system
}

FILES:${PN} += "${libdir}/tmpfiles.d/lte-virtual-regs.conf"
CONFFILES:${PN} = "${sysconfdir}/unipi/lte.conf"

SYSTEMD_SERVICE:${PN} = "unipi-lte.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
RDEPEND:${PN}+= "python3-pyserial"
