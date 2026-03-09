SUMMARY = "compact image for Unipi PLC"
DESCRIPTION = "unipi.technology Base-os"
RECIPE_MAINTAINER = "Miroslav Ondra <ondra@unipi.technology>"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

IMAGE_FSTYPES ?= "ext4 wic"

IMAGE_FEATURES:append = " package-management"
IMAGE_FEATURES:append = " ssh-server-openssh"

IMAGE_INSTALL:append = " packagegroup-compat-base-utils"
IMAGE_INSTALL:append = " packagegroup-security-tpm2"
IMAGE_INSTALL:append = " os-release"
IMAGE_INSTALL:append = " u-boot-tools-mkimage"

# this dependency moved to u-boot-script
#IMAGE_INSTALL:append = " ${@'initramfs-boot' if d.getVar('INITRAMFS_IMAGE', True) else ''}"

#IMAGE_INSTALL:append = " audit auditd audispd-plugins"
#IMAGE_INSTALL:append = " mc"
#IMAGE_INSTALL:append = " mbpoll"

IMAGE_FEATURES:remove = "splash"

inherit core-image extrausers

RDEPENDS:packagegroup-base:remove = "packagegroup-base-3g"
RDEPENDS:packagegroup-base-extended:remove = "packagegroup-base-3g"

#IMAGE_FEATURES:append = " allow-root-login"
#IMAGE_FEATURES:append = " allow-empty-password"
#IMAGE_FEATURES:append = " empty-root-password"

