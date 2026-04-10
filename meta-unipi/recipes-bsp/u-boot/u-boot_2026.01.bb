
require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

SRCREV_FORMAT = "upstream_unipi"
SRCREV_upstream = "127a42c7257a6ffbbd1575ed1cbaa8f5408a44b3"
SRCREV_unipi = "b4a53e930904a07dd49e51a4ad84a79ad21b13c8"

SRC_URI = "git://source.denx.de/u-boot/u-boot.git;protocol=https;branch=master;name=upstream \
           git://github.com/UnipiTechnology/unipi-u-boot.git;protocol=https;branch=main;name=unipi;destsuffix=unipi"

SRC_URI:remove:rpi = " file://0001-rpi-always-set-fdt_addr-with-firmware-provided-FDT-address.patch"

do_patch_uboot () {
    cd "${WORKDIR}/unipi" && cp -r unipi/* "${S}"
    cd "${S}" && find "${WORKDIR}/unipi/patches-u" -name \*.patch -exec patch -p 1 -i \{\} \;
}

addtask do_patch_uboot before do_patch after do_unpack

DEPENDS += "bc-native dtc-native python3-pyelftools-native gnutls-native"
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:unipi_edge = "unipi_edge"
COMPATIBLE_MACHINE:unipi_neuron = "unipi_neuron"
RDEPENDS:${PN} += "u-boot-default-script"

# This U-boot is incomaptible with mender-uboot
# Mender is supported directly by this version
MENDER_FEATURES_DISABLE:append = " mender-uboot"
