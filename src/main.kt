/**
 * Created by yangxb on 2017/7/1.
 */
fun main(aaa: Array<String>) {
    println("Main start")

    Observable.create(object : OnSubscribe<Int> {
        override fun call(s: Subscriber<Int>) {
            println(Thread.currentThread().name)
            println("Observable_OnSubscribe_call")
            for (i in 1..100) {
                s.onNext(i)
            }
            s.onCompleted()
        }
    })
            .observeOn()
            .subsrcibeOn()
            .trans(object : Transformer<Int, String> {
        override fun transform(t: Int): String {
            return "String is $t"
        }
    })
            .subscribe(object : Subscriber<String>() {
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

        override fun onNext(t: String) {
            println(Thread.currentThread().name + " Result is $t")
            println("subscribe_Subscriber_onNext")
        }
    })
}