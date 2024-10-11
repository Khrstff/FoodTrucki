package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.states

import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ProductoEntity

data class ProductosStates (
    val listaProductos: List<ProductoEntity?> = emptyList()
)