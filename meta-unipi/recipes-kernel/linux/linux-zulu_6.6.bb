# Copyright 2013-2016 (C) Freescale Semiconductor
# Copyright 2017-2023 (C) NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Copyright 2024 (C) Unipi Technology, spol. s r.o.
# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "Linux Kernel provided and supported by Unipi Technology"
DESCRIPTION = "Linux Kernel provided and supported by Unipi Technology for Zulu board."

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

#!# require recipes-kernel/linux/linux-imx.inc

KERNEL_DEVICETREE_32BIT_COMPATIBILITY_UPDATE = "1"

SRCBRANCH = "lf-6.6.y"
SRCREV = "lf-6.6.3-1.0.0"

SRCBRANCH_patch = "dev-6.6.3"
SRCREV_patch = "464557f6a55e8c7dc036d2b736cfd212d317fc7e"

SRC_URI = "git://github.com/nxp-imx/linux-imx;protocol=https;branch=${SRCBRANCH};name=upstream \
           git://git.unipi.technology/UniPi/zulu-kernel;protocol=https;branch=${SRCBRANCH_patch};name=patch;destsuffix=patches"

#SRC_URI[patch.sha256sum] = "464557f6a55e8c7dc036d2b736cfd212d317fc7e"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.6.3"
LOCALVERSION = "-1.0"

KBUILD_DEFCONFIG:zulu = "unipi-zulu_defconfig"
KERNEL_EXTRA_ARGS += "dtstree=${WORKDIR}/patches/dts"

DEFAULT_PREFERENCE = "1"

COMPATIBLE_MACHINE = "(zulu)"

do_patch_kernel () {
    cd "${S}" && find "${WORKDIR}/patches/patches" -name \*.patch -exec patch -p 1 -i \{\} \;
    cd "${S}" && find "${WORKDIR}/patches/configs" -type f -exec cp \{\} arch/arm64/configs \;
    cd "${WORKDIR}/patches" && ln -sf "${S}" linux-imx
}

# override function in recipe
get_real_dtb_path_in_kernel () {
	dtb="$1"
	dtb_path="${WORKDIR}/patches/dts/$dtb"
	if [ ! -e "$dtb_path" ]; then
		dtb_path="${B}/arch/${ARCH}/boot/$dtb"
	fi
	echo "$dtb_path"
}

addtask do_patch_kernel before do_kernel_metadata after do_unpack
