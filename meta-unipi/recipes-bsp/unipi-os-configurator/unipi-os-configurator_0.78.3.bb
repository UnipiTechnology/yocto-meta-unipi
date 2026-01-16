SUMMARY = "Unipi OS configurator"
DESCRIPTION = "Binary tools and common data to detect hardware \
changes and configure various OS aspects like device tree overlays, \
udev rules, hostname ... \
"
HOMEPAGE = "http://unipi.technology/"
SECTION = "base"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRCREV = "88fbd8b603bd94aadd990b8069d2049d4f501949"
SRC_URI = "git://git.unipi.technology/UniPi/os-configurator/unipi-os-configurator;protocol=https;branch=dev-trixie \
"

inherit systemd pkgconfig

S = "${WORKDIR}/git/src"

RDEPENDS:${PN} = "python3-core (>=3.11) systemd bash"

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
