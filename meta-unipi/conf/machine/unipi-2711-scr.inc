
def make_dtb_boot_files(d):
    # Generate IMAGE_BOOT_FILES entries for device tree files listed in
    # KERNEL_DEVICETREE.

    if d.getVar('RPI_USE_U_BOOT') == '1':
        alldtbs = d.getVar('BOOTIMG_DEVICETREE')
    else:
        alldtbs = d.getVar('KERNEL_DEVICETREE')

    # DTBs may be built out of kernel with devicetree.bbclass
    if not alldtbs:
        return ''

    def transform(dtb):
        base = os.path.basename(dtb)
        if dtb.endswith('dtbo') or base == 'overlay_map.dtb':
            return '{};{}'.format(base, dtb)
        elif dtb.endswith('dtb'):
            return base

    return ' '.join([transform(dtb) for dtb in alldtbs.split(' ') if dtb])
