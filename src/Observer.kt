/**
 * Created by yangxb on 2017/7/1.
 */
interface Observer<T> {
    fun onCompleted()
    fun onError(t: Throws)
    fun onNext(t: T)
}