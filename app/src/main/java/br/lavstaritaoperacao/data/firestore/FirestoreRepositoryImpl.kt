package br.lavstaritaoperacao.data.firestore

import br.lavstaritaoperacao.domain.model.Service
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database

class FirestoreRepositoryImpl(

): FirestoreRepository {

    private val database = Firebase.database
    private val servicesRef = database.getReference("services")

    override suspend fun saveService(service: Service) {
        val serviceId = servicesRef.push().key
        if (serviceId != null) {
            service.serviceId = serviceId.toInt()
            servicesRef.child(serviceId).setValue(service)
        }
    }

//    override suspend fun getServices(): MutableList<Service> {
//        servicesRef.get().addOnSuccessListener { snapshot ->
//            val services = mutableListOf<Service>()
//            snapshot.children.forEach { dataSnapshot ->
//                val service = dataSnapshot.getValue(Service::class.java)
//                service?.let { services.add(it) }
//            }
//            return@addOnSuccessListener services
//        }
//        return
//    }

}