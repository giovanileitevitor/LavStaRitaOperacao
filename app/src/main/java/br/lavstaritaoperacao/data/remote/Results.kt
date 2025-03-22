package br.lavstaritaoperacao.data.remote

sealed class Results<T>
data class SuccessResults<T>(val body: T) : Results<T>()
data class ErrorResults<T>(val error: ApiError) : Results<T>()

//fun <T, O> Response<T>.create(mapper: Mapper<T, O>): Results<O> {
//    return transformResponse(this).converter(mapper)
//}
//
//abstract class Mapper<CLASS_IN, CLASS_OUT> {
//    abstract fun transform(item: CLASS_IN): CLASS_OUT
//}