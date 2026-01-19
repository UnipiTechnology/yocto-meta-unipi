SUMMARY = "Unipi OS configurator data"
DESCRIPTION = "Data for OS configurator"

HOMEPAGE = "http://unipi.technology/"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV:unipi_edge = "808c91291be96497e26bf05aa64915be16dcd9a6"
SRC_URI:unipi_edge = "git://github.com/UniPiTechnology/os-configurator-data-edge.git;protocol=https;branch=main \
"

inherit systemd pkgconfig

S = "${WORKDIR}/git"

# split into packages
PACKAGES =+ " unipi-os-configurator-data-base unipi-os-configurator-data-auto"
RDEPENDS:${PN}-auto = "unipi-os-configurator-data-base"

# Nothing to build, just install
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install:unipi_edge() {
    install -d ${D}/usr/lib/unipi
    install -m 644 ${S}/unipi_values.py ${D}/usr/lib/unipi
    install -d ${D}/usr/share/unipi-os-configurator/udev
    install -m 644 ${S}/udev/*.rules ${D}/usr/share/unipi-os-configurator/udev
    cp -r ${S}/files/* ${D}
    rm -rf ${D}/usr/lib/tmpfiles.d
    rm -f ${D}/etc/modprobe.d/neuron-blacklist.conf
    case "${MACHINE}" in
        unipi-e410) urule=e410.rules ;;
        unipi-e411) urule=e411.rules ;;
        unipi-e412) urule=e412.rules ;;
        unipi-e413) urule=e413.rules ;;
        unipi-e414) urule=e414.rules ;;
        *) unset urule ;;
    esac
    if [ -n "$urule" ]; then
        install -d ${D}/etc/udev/rules.d
        ln -s /usr/share/unipi-os-configurator/udev/$urule ${D}/etc/udev/rules.d/50-$urule
    fi
}

FILES:${PN}-base += " \
    /usr/lib/unipi/fwi2c-check.sh \
    /usr/lib/systemd/network/* \
    /usr/lib/systemd/system.conf.d/* \
    /usr/lib/modprobe.d/* \
    /usr/lib/udev/rules.d \
    /usr/share/initramfs-tools/modules.d/unipi \
    /etc/sysctl.d/* \
    /etc/udev/rules.d/* \
    /etc/modules-load.d/* \
"
FILES:${PN}-auto += "/usr/lib/unipi/unipi_values.py \
    /usr/lib/unipi/run.d/* \
    /usr/share/unipi-os-configurator/* \
    /etc/bootcmd.d \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

BBCLASSEXTEND = "native"
