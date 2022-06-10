package com.jaemin.sunflower_clone.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * 데이터 연산을 다루기 위한 Repository 모듈
 *
 * PlantDao의 Flow로부터 가져오는 것은 안전하다. Room은 코루틴을 지원하고,
 * 메인스레드 밖에서 쿼리가 작동한다.
 */


@Singleton
class PlantRepository @Inject constructor(private val plantDao: PlantDao) {

    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)
}