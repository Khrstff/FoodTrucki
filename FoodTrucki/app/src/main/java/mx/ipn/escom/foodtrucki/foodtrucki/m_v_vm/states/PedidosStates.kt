package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.states

import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.PedidoEntity

data class PedidosStates (
    val listaPedidos: List<PedidoEntity?> = emptyList()
)