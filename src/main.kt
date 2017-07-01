/**
 * Created by yangxb on 2017/7/1.
 */
fun main(aaa: Array<String>) {
    println("Main start")

    Observable.create(object : OnSubscribe<Int> {
        override fun call(subscribe: Subscriber<Int>) {
            println(Thread.currentThread().name)
            println("Observable_OnSubscribe_call")
            for (i in 1..100){
                subscribe.onNext(i)
            }

            subscribe.onCompleted()
        }
    }).observeOn().subsrcibeOn().subscribe(object : Subscriber<Int>() {
        override fun onCompleted() {
            println(Thread.currentThread().name)
            println("subscribe_Subscriber_onCompleted")
        }

        override fun onError(t: Throws) {
            println(Thread.currentThread().name)
            println("subscribe_Subscriber_onError")
        }

        override fun onStart() {
            println(Thread.currentThread().name)
            println("subscribe_Subscriber_onStart")
        }

        override fun onNext(t: Int) {
            println(Thread.currentThread().name + "int is $t")
            println("subscribe_Subscriber_onNext")
        }
    })
}