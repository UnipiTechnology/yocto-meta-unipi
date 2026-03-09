SUMMARY = "Global overlayfs mounts"
DESCRIPTION = "Install systemd units and configurations for \
               automounted overlayfs.\
               Requires overlayfs in DISTRO_FEATURES. \
               Requires OVERLAYFS_MOUNT_POINT[xxxx] in machine configuration,\
                        OVERLAYFS_WRITABLE_PATHS[xxxx] in local conf,\
               optional OVERLAYFS_QA_SKIP[mnt-overlay] = "mount-configured" \
"

HOMEPAGE = "http://unipi.technology/"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd overlayfs

PACKAGES = "overlayfs-all"

FILES:${PN} += " \
    /usr/lib/systemd/system \
"
