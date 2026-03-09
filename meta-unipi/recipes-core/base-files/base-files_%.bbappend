FILESEXTRAPATHS:prepend := "${THISDIR}/base-files:"

SRC_URI:append:unipi_edge = " file://fstab.in \
    file://fstab.edge.append \
    "

# UNIPI_FSTAB_ROOT_OPTS ?= ",x-systemd.growfs"
# UNIPI_FSTAB = "1"

do_compile:append() {
    sed "s/@@FSTAB_ROOT_OPTS@@/${UNIPI_FSTAB_ROOT_OPTS}/" ${WORKDIR}/fstab.in > ${WORKDIR}/fstab
}

do_compile:append:unipi_edge() {
    if [ "${UNIPI_FSTAB}" = "1" ]; then
        cat ${WORKDIR}/fstab.edge.append >> ${WORKDIR}/fstab
    fi
}

do_install:append () {
    install -m 0755 -d ${D}/boot/firmware
}
