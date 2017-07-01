/**
 * Created by yangxb on 2017/7/1.
 */
class Observable<T>(val onSubscribe: OnSubscribe<T>) {

    fun subscribe(subscriber: Subscriber<T>) {
        subscriber.onStart()
        onSubscribe.call(subscriber)
    }

    fun <R> trans(transformer: Transformer<T, R>): Observable<R> =
            create(object : OnSubscribe<R>{
                override fun call(s: Subscriber<R>) {
                    this@Observable.subscribe(object : Subscriber<T>(){
                        override fun onCompleted() {
                            s.onCompleted()
                        }

                        override fun onError(t: Throws) {
                            s.onError(t)
                        }

                        override fun onStart() {
                            s.onStart()
                        }

                        override fun onNext(t:T) {
                            s.onNext(transformer.transform(t))
                        }
                    })
                }
            })

    fun subsrcibeOn(): Observable<T> =
            create(object : OnSubscribe<T> {
                override fun call(s: Subscriber<T>) {
                    Thread({
                        println("Thread start")
                        this@Observable.onSubscribe.call(s)
                    }).start()
                }
            })

    fun observeOn(): Observable<T> =
            create(object : OnSubscribe<T> {
                override fun call(s: Subscriber<T>) {
                    this@Observable.onSubscribe.call(object : Subscriber<T>() {
                        override fun onCompleted() {
                            Thread({
                                println("Thread start")
                                println("observeOn_observeOn")
                                s.onCompleted()
                            }).start()

                        }

                        override fun onError(t: Throws) {
                            Thread({
                                println("Thread start")
                                println("observeOn_onError")
                                s.onError(t)
                            }).start()
                        }

                        override fun onStart() {
                            Thread({
                                println("Thread start")
                                println("observeOn_onStart")
                                s.onStart()
                            }).start()
                        }

                        override fun onNext(t: T) {
                            Thread({
                                println("Thread start")
                                println("observeOn_onNext")
                                s.onNext(t)
                            }).start()
                        }

                    })
                }

            })



    companion object A {
        @JvmStatic
        fun <T> create(onSubscribe: OnSubscribe<T>): Observable<T> =
                Observable(onSubscribe)
    }
}

interface Transformer<T, R> {
    fun transform(t: T): R
}