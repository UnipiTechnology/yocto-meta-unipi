SUMMARY = "Unipi OS configurator"
DESCRIPTION = "Binary tools and common data to detect hardware \
changes and configure various OS aspects like device tree overlays, \
udev rules, hostname ... \
"
HOMEPAGE = "http://unipi.technology/"
SECTION = "base"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit systemd pkgconfig

S = "${WORKDIR}/git/src"


EXTRA_OEMAKE = " PROJECT_VERSION=${PV}"
TARGET_CC_ARCH += "${LDFLAGS}"

do_install:append() {
    oe_runmake install DESTDIR=${D}

    install -d ${D}/usr/lib
    cp -r ${S}/../files/* ${D}/
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/../debian/unipi-os-configurator.clear-bootcount.service ${D}${systemd_unitdir}/system/clear-bootcount.service
    install -m 0644 ${S}/../debian/unipi-os-configurator.unipicheck.service ${D}${systemd_unitdir}/system/unipicheck.service
    rm -f  ${D}/etc/sysctl.d/80-ram-overcommit.conf
    rm -rf ${D}/usr/share/keyrings
    if [ -n "${MENDER_ROOTFS_PART_A_NUMBER}" ] && [ -n "${MENDER_ROOTFS_PART_B_NUMBER}" ]; then
        printf "A=%s\nB=%s\n" "${MENDER_ROOTFS_PART_A_NUMBER}" "${MENDER_ROOTFS_PART_B_NUMBER}" > ${D}${sysconfdir}/default/switchboot
    fi
}

# split into packages
PACKAGES =+ " unipi-os-configurator-base unipi-os-configurator-auto unipi-os-configurator-switchboot"

FILES:${PN}-base += "${bindir}/unipiid \
                     ${libdir}/unipi/unipihostname \
                     ${libdir}/unipi/uhelper \
                     ${libdir}/unipi/sw_485 \
                     ${libdir}/udev/rules.d/90-unipi-id.rules \
"
FILES:${PN}-auto += "${sbindir}os-configurator \
                     ${libdir}/unipi/os-configurator.py \
                     ${libdir}/unipi/run.d/* \
                     ${systemd_unitdir}/system/unipicheck.path \
                     ${systemd_unitdir}/system/unipicheck.service \
"

FILES:${PN}-switchboot += "${sbindir}/switchboot \
                           ${libdir}/unipi/bootcount \
                           ${libdir}/udev/rules.d/10-bootcount.rules \
                           ${systemd_unitdir}/system/clear-bootcount.service \
                           ${sysconfdir}/default/switchboot \
"

SYSTEMD_SERVICE:${PN}-switchboot = "clear-bootcount.service"
SYSTEMD_SERVICE:${PN}-auto = "unipicheck.path"

DEPENDS += "i2c-tools openssl"
RDEPENDS:${PN}-base = "unipi-os-configurator-data-base i2c-tools"
RDEPENDS:${PN}-auto = "python3-core (>=3.11) systemd ${PN}-base unipi-os-configurator-data-auto"
RDEPENDS:${PN}-switchboot = "systemd util-linux-sfdisk ${PN}-base"

BBCLASSEXTEND = "native"
