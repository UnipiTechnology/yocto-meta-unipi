FILESEXTRAPATHS:prepend := "${THISDIR}/base-files:"

SRC_URI:append:rpi = " file://fstab.in \
    file://fstab.rpi.append \
    "

# UNIPI_FSTAB_ROOT_OPTS ?= ",x-systemd.growfs"
# UNIPI_FSTAB = "1"

do_compile:append() {
    sed "s/@@FSTAB_ROOT_OPTS@@/${UNIPI_FSTAB_ROOT_OPTS}/" ${WORKDIR}/fstab.in > ${WORKDIR}/fstab
}

do_compile:append:rpi() {
    if [ "${UNIPI_FSTAB}" = "1" ]; then
        cat ${WORKDIR}/fstab.rpi.append >> ${WORKDIR}/fstab
    fi
}

do_install:append:rpi () {
    install -m 0755 -d ${D}/boot/firmware
}
