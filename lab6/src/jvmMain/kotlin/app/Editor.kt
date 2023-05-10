package app

class Editor {

    private var content = listOf<Int>()

    fun getContent() = content

    fun save() = AppMemory(content)

    fun restore(clazz: AppMemory) {
        content = clazz.getContent()
    }
}