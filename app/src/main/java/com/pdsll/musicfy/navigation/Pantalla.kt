package com.pdsll.musicfy.navigation


const val DETAIL_ARGUMENT_KEY = "Id"
const val DETAIL_ARGUMENT_KEY2 = "Link"

sealed class Pantalla(val route: String){
    object LogIn : Pantalla("log_in")
    object SignUp : Pantalla("sign_up")
    object CheckBox : Pantalla("check_box")
    object Menu : Pantalla("menu")
    object Busqueda : Pantalla("busqueda")
    object Ajustes : Pantalla("ajustes")
    object Inicio : Pantalla("inicio")
    object Mapa : Pantalla("mapa")
    object AbrirGaleria : Pantalla("abrir_galeria")
    object Imagen : Pantalla("image_screen")
    object Perfil : Pantalla("perfil")
    object CartaDetallada : Pantalla("carta_detallada/{$DETAIL_ARGUMENT_KEY}/{$DETAIL_ARGUMENT_KEY2}"){
        fun passId(id: String): String{
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
        fun passLink(link: String): String{
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY2}", newValue = link)
        }
        fun passLinkAndId(
            id: String,
            link: String
        ): String{
            return "carta_detallada/$id$link"
        }
    }
    object RecuContr : Pantalla("recu_contr")
    object favoritos : Pantalla("favoritos")
    object Comentarios : Pantalla("comentarios")
    object UpdateUser : Pantalla("update_user")
    object Tema : Pantalla("tema")
    object UpdateContr : Pantalla("update_contr")
    object Feed : Pantalla("feed")
}
