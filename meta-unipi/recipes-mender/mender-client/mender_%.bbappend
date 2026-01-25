FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://mender-inventory-hostinfo"

# add dependency on libubootenv-bin if mender-uboot feature not set, but uboot is installed
RDEPENDS:mender-update:append = "${@bb.utils.contains('RPI_USE_U_BOOT', '1', ' libubootenv-bin', '', d)}"

do_install:append() {
    # replace inventory scripts
    rm -f ${D}${datadir}/mender/inventory/mender-inventory-hostinfo
    install -m 755 ${WORKDIR}/mender-inventory-hostinfo ${D}${datadir}/mender/inventory/mender-inventory-hostinfo
}

RDEPENDS:mender-update:append:mender-growfs-data:mender-systemd = " util-linux-sfdisk util-linux-partx"
RDEPENDS:mender-update:remove = "parted"
