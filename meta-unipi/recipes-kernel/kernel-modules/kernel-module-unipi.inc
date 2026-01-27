# Copyright 2024 (C) Unipi Technology, spol. s r.o.
SUMMARY = "Kernel modules for Unipi PLCs"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://../../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "git://github.com/UniPiTechnology/unipi-kernel-modules.git;protocol=https;branch=main"
SRCREV = "cbf094c8b3d88927d8c2987fdf99c6e92c7a55d8"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

#COMPATIBLE_MACHINE = "(zulu)"
