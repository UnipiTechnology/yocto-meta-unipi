#
# Copyright (C) 2026 Unipi Technology spol. s r.o.
#

SUMMARY = "Set of base utils without incompatible Licenses"
DESCRIPTION = "Package group bringing in packages needed to provide much of the base utils"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

VIRTUAL-RUNTIME_vim ?= "vim-tiny"

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "\
    base-passwd \
    bind-utils \
    uutils-coreutils \
    e2fsprogs \
    ed \
    file \
    iputils \
    iproute2 \
    ${@bb.utils.contains("MACHINE_FEATURES", "keyboard", "kbd", "", d)} \
    kmod \
    ncurses-tools \
    net-tools \
    procps \
    psmisc \
    shadow-base \
    unzip \
    util-linux \
    ${VIRTUAL-RUNTIME_vim} \
    xz \
"
