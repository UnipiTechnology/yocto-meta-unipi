SUMMARY = "Unipi OS configurator"
DESCRIPTION = "Binary tools and common data to detect hardware \
changes and configure various OS aspects like device tree overlays, \
udev rules, hostname ... \
"
HOMEPAGE = "http://unipi.technology/"
SECTION = "base"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

RDEPENDS:${PN} = "python3 (>=3.11) systemd"
SRC_URI = "git://git.unipi.technology/UniPi/os-configurator/unipi-os-configurator;protocol=https;branch=main \
  file://Makefile.patch \
"

SRCREV = "37fe4f508ca1fb25c164a872d7d315b13fb48141"

inherit systemd pkgconfig

S = "${WORKDIR}/git/src"

EXTRA_OEMAKE = " PROJECT_VERSION=${PV}"

do_install:append() {
    oe_runmake install DESTDIR=${D}

    install -d ${D}/usr/lib
    cp -r ${S}/../files/lib/* ${D}/usr/lib
    cp -r ${S}/../files/opt ${D}
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/../debian/unipi-os-configurator.unipihostname.service ${D}${systemd_unitdir}/system/unipihostname.service
    install -m 0644 ${S}/../debian/unipi-os-configurator.unipicheck.service ${D}${systemd_unitdir}/system/unipicheck.service
}

FILES:${PN} += "/opt/unipi/* \
               ${systemd_unitdir}/system/unipihostname.service \
"

SYSTEMD_SERVICE:${PN} = "unipihostname.service unipicheck.service"

BBCLASSEXTEND = "native"
