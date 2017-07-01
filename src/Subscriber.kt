/**
 * Created by yangxb on 2017/7/1.
 */
abstract class Subscriber<T> : Observer<T>{
    abstract fun onStart()
}