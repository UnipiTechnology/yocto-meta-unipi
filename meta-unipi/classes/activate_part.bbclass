

fix_image_sdimg () {
    dev=${IMGDEPLOYDIR}/${IMAGE_NAME}.sdimg
    if [ -r "$dev" ]; then
        if sfdisk -d "$dev" | grep -q 'label: gpt'; then
            sfdisk --no-reread --no-tell-kernel --quiet --part-attrs "$dev" 2 LegacyBIOSBootable
            sfdisk --no-reread --no-tell-kernel --quiet --part-attrs "$dev" 1 ""
        else
            sfdisk --no-reread --no-tell-kernel --quiet --activate "$dev" 2
        fi
    fi
}

IMAGE_CMD:sdimg:append () {
   fix_image_sdimg
}
#IMAGE_POSTPROCESS_COMMAND += "fix_image_sdimg"
