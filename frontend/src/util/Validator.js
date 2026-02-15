export default class Validator {
    static textValidator(text) {
        if(text.trim().length < 1) {
            return false
        }
        return true
    }

    static emailValidor(email) {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        return regex.test(email)
    }

    static passwordValidator(password) {
        // At least:
        // 1- 1 lowcase letter
        // 2- 1 uppercase letter
        // 3- 1 number
        // 4- 1 special character @$!%*^#()[]{}|\/+_.:;=~
        // 5- It must not have spaces neither < >
        // 6- Min 8 characters
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*^#()[\]{}|\\/+_.:;=~])[^\s<>]{8,}$/
        return regex.test(password)
    }

    static price(price) {
        if(price < 0 || price === null || price === undefined) return false
        return true
    }

    static description(desc) {
        if(desc.trim().length < 30) return false
        return true
    }
}