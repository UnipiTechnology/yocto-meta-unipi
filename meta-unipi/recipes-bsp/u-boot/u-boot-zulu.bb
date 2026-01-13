# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Copyright (C) 2017-2023 NXP
# Copyright 2024 (C) Unipi Technology, spol. s r.o.

require recipes-bsp/u-boot/u-boot.inc
require u-boot-zulu-common.inc

#!# inherit imx-boot-container

PROVIDES += "u-boot"
# u-boot-default-script"
DEPENDS += "python3-setuptools-native"
#RDEPENDS:${PN} += "u-boot-script-zulu"


## tag=lf-6.1.1-1.0.0"
SRCBRANCH = "lf_v2022.04"
SRCREV_upstream = "7376547b9e424b2d0f42dfe96394168c781ca297"
SRCBRANCH_unipi = "dev-bootcount"
SRCREV_unipi = "33d18234c9c43510711c21b8e96b82c44e01a9c1"

SRC_URI = "git://github.com/nxp-imx/uboot-imx;protocol=https;branch=${SRCBRANCH};name=upstream \
           git://git.unipi.technology/UniPi/zulu-u-boot;protocol=https;branch=${SRCBRANCH_unipi};name=unipi;destsuffix=unipi"

SRC_URI[unipi.sha256sum] = "eb94414f952444289c0af8ad82afa0dc2d07e08f9000ccd2cc39f11da5b494ea"
SRCREV_FORMAT = "upstream_unipi"


do_patch_uboot () {
    cd "${S}" && find "${WORKDIR}/unipi/patches-u" -name \*.patch -exec patch -p 1 -i \{\} \;
}

addtask do_patch_uboot before do_patch after do_unpack

COMPATIBLE_MACHINE = "(zulu)"
