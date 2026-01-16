SUMMARY = "Unipi OS configurator"
DESCRIPTION = "Binary tools and common data to detect hardware \
changes and configure various OS aspects like device tree overlays, \
udev rules, hostname ... \
"
HOMEPAGE = "http://unipi.technology/"
SECTION = "base"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://../../GPL-2.0-or-later;md5=fed54355545ffd980b814dab4a3b312c"

RDEPENDS:${PN} = "python3-core (>=3.11) systemd bash"
SRC_URI = "git://git.unipi.technology/UniPi/os-configurator/unipi-os-configurator;protocol=https;branch=dev-trixie \
  file://GPL-2.0-or-later \
  file://Makefile.patch  \
"

SRCREV = "752cf5e1ac8db47b59fb6ba12b05a37a830eda8d"

inherit systemd pkgconfig

S = "${WORKDIR}/git/src"

EXTRA_OEMAKE = " PROJECT_VERSION=${PV}"
TARGET_CC_ARCH += "${LDFLAGS}"

do_install:append() {
    oe_runmake install DESTDIR=${D}

    install -d ${D}/usr/lib
    cp -r ${S}/../files/usr/lib/* ${D}/usr/lib
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/../debian/unipi-os-configurator.clear-bootcount.service ${D}${systemd_unitdir}/system/clear-bootcount.service
    install -m 0644 ${S}/../debian/unipi-os-configurator.unipicheck.service ${D}${systemd_unitdir}/system/unipicheck.service
}

FILES:${PN} += "/usr/lib/unipi/* \
                ${systemd_unitdir}/system/sys-devices-platform-unipi\x2did.device.d/timeout.conf \
"

SYSTEMD_SERVICE:${PN} = "clear-bootcount.service unipicheck.service"

BBCLASSEXTEND = "native"
