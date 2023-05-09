package app.utils

interface IObservable {

    val observers: ArrayList<IObserver>

    fun add(observer: IObserver) {
        observers.add(observer)
    }
    fun remove(observer: IObserver) {
        observers.remove(observer)
    }
    fun sendUpdateEvent(value: String) {
        observers.forEach { it.update(value) }
    }
}