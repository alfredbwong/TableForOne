package studio.alphared.tableforone.utils

import studio.alphared.tableforone.objects.reminder.MealReminder

class TimeDateComparator : Comparator<MealReminder>{
    override fun compare(o1: MealReminder, o2: MealReminder): Int {
        val yearCompare = o1.mealYear.compareTo(o2.mealYear)
        val monthCompare = o1.mealMonth.compareTo(o2.mealMonth)
        val dayCompare = o1.mealDay.compareTo(o2.mealDay)
        val hourCompare = o1.mealHour.compareTo(o2.mealHour)
        val minuteCompare = o1.mealMinute.compareTo(o2.mealMinute)

        if (yearCompare != 0 ){
            return yearCompare
        }
        if (monthCompare != 0){
            return monthCompare
        }
        if (dayCompare != 0){
            return dayCompare
        }
        if (hourCompare != 0){
            return hourCompare
        }
        if (minuteCompare != 0){
            return minuteCompare
        }
        return 0
    }
}