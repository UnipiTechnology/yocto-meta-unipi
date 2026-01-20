SUMMARY = "compact image for Unipi PLC"
DESCRIPTION = "unipi.technology Base-os"
RECIPE_MAINTAINER = "Miroslav Ondra <ondra@unipi.technology>"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

IMAGE_FSTYPES = "ext4 wic"
IMAGE_OVERHEAD_FACTOR = "1.1"

IMAGE_FEATURES:append = " package-management"
IMAGE_FEATURES:append = " ssh-server-openssh"

IMAGE_INSTALL:append = " packagegroup-core-base-utils"
IMAGE_INSTALL:append = " packagegroup-security-tpm2"
IMAGE_INSTALL:append = " mbpoll"
IMAGE_INSTALL:append = " os-release"
IMAGE_INSTALL:append = " procps"
IMAGE_INSTALL:append = " file"
IMAGE_INSTALL:append = " mc"
IMAGE_INSTALL:append = " u-boot-tools-mkimage"
IMAGE_INSTALL:append = " audit auditd audispd-plugins"

IMAGE_FEATURES:remove = "splash"

inherit core-image extrausers

RDEPENDS:packagegroup-base:remove = "packagegroup-base-3g"
RDEPENDS:packagegroup-base-extended:remove = "packagegroup-base-3g"

#IMAGE_FEATURES:append = " allow-root-login"
#IMAGE_FEATURES:append = " allow-empty-password"
#IMAGE_FEATURES:append = " empty-root-password"
