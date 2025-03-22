package br.lavstaritaoperacao.ui.operation.configuration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

class ConfigurationViewModel(
    private var context: Context,
    private val globalUseCase: GlobalUseCase
) : ViewModel() {

    val onLoading: LiveData<Boolean> get() = _onLoading
    private val _onLoading: MutableLiveData<Boolean> = MutableLiveData()

    val onError: LiveData<String> get() = _onError
    private val _onError: MutableLiveData<String> = MutableLiveData()

    val servicesAdded: LiveData<List<Service>> get() = _servicesAdded
    private val _servicesAdded: MutableLiveData<List<Service>> = MutableLiveData()

    val itemsAdded: LiveData<List<Item>> get() = _itemsAdded
    private val _itemsAdded: MutableLiveData<List<Item>> = MutableLiveData()

    val file: LiveData<Workbook> get() = _file
    private val _file: MutableLiveData<Workbook> = MutableLiveData()

    fun getAllServices(){
        viewModelScope.launch {
            _servicesAdded.value = globalUseCase.getALLServices()
        }
    }

    fun getAllItems(){
        viewModelScope.launch {
            _itemsAdded.value = globalUseCase.getAllItems()
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            _onLoading.value = true
            globalUseCase.deleteAllServices()
            globalUseCase.deleteAllItems()

            getAllServices()
            getAllItems()
            _onLoading.value = false
        }
    }

    fun shareAll(){
        viewModelScope.launch {
            val services = globalUseCase.getALLServices()
            val items = globalUseCase.getAllItems()
            val workbook: Workbook = XSSFWorkbook()

            val planServices = workbook.createSheet("ServicesTB")
            val headerRowTabela1 = planServices.createRow(0)
            headerRowTabela1.createCell(0).setCellValue("Cliente")
            headerRowTabela1.createCell(1).setCellValue("Telefone")
            headerRowTabela1.createCell(2).setCellValue("dataEntrada")
            headerRowTabela1.createCell(3).setCellValue("Preço")
            headerRowTabela1.createCell(3).setCellValue("QtdItems")

            for ((index, service) in services.withIndex()) {
                val row = planServices.createRow(index + 1)
                row.createCell(0).setCellValue(service.clientName)
                row.createCell(1).setCellValue(service.clientPhone)
                row.createCell(2).setCellValue(service.dataIn)
                row.createCell(3).setCellValue(service.price)
                row.createCell(4).setCellValue(service.qtdItems.toString())
            }

            val planItems = workbook.createSheet("ItemsTB")
            val headerRowTabela2 = planItems.createRow(0)
            headerRowTabela2.createCell(0).setCellValue("ItemID")
            headerRowTabela2.createCell(1).setCellValue("Nome")
            headerRowTabela2.createCell(2).setCellValue("qtdItems")
            headerRowTabela2.createCell(3).setCellValue("ServiceId")

            for ((index, item) in items.withIndex()) {
                val row = planServices.createRow(index + 1)
                row.createCell(0).setCellValue(item.id.toString() )
                row.createCell(1).setCellValue(item.name)
                row.createCell(2).setCellValue(item.qtd.toString())
                row.createCell(3).setCellValue(item.serviceId.toString())
            }

            val filePath = "${context.getExternalFilesDir(null)}/dados_exportados.xls"
            val outputStream : FileOutputStream = FileOutputStream(filePath)
            workbook.write(outputStream)
            workbook.close()
            outputStream.close()

            _file.value = workbook

        }
    }
}