package app.updated_shapes

open class Shape(
    var size: Int = 0,
    var color: String = "Color",
    var name: String = "Name"
) {

    open fun getAbout() = "$name with $color with $size"
}