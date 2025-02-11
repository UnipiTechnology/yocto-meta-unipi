# Copyright 2024 (C) Unipi Technology, spol. s r.o.
SUMMARY = "Kernel modules for Unipi PLCs"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://../../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# For backwards compatibility
PROVIDES += "kernel-module-unipi-rfkill"
#RPROVIDES:${PN} = "kernel-module-unipi-rfkill"

SRCBRANCH = "master"
SRC_URI = "git://git.unipi.technology/UniPi/unipi-kernel-modules;protocol=https;branch=${SRCBRANCH}"
SRCREV = "6b058d4b580b6fb3aaf7c7214734124735975e68"

S = "${WORKDIR}/git/modules/unipi-rfkill"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

#COMPATIBLE_MACHINE = "(zulu)"
