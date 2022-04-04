package base.model

interface Mask<T : Flag> {

  val mask: Long

  val values: Array<T>

  fun getFlag(flags: Long): T? {
    return values.firstOrNull { flags and mask == it.flag }
  }

  fun setFlag(flags: Long, value: Flag): Long {
    return flags and mask.inv() or (value.flag and mask)
  }


}