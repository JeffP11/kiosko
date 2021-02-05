package contifico.kiosko_app.sale

class MenuItem {
    var icon: String
    var text: String
    var isSelected = false
    var numNotifications = 0
    var isNotifications = false

    constructor(icon: String, text: String) {
        this.icon = icon
        this.text = text
    }

    constructor(icon: String, text: String, selected: Boolean) {
        this.icon = icon
        this.text = text
        isSelected = selected
    }

    constructor(icon: String, text: String, numNotifications: Int, notifications: Boolean) {
        this.icon = icon
        this.text = text
        this.numNotifications = numNotifications
        isNotifications = notifications
    }

}
