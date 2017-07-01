/**
 * Created by yangxb on 2017/7/1.
 */
interface OnSubscribe<T> {
    fun call(s: Subscriber<T>)
}