package ie.setu.diary.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.setu.diary.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "entries.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<DiaryModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class DiaryJSONStore(private val context: Context) : DiaryStore {

    var entries = mutableListOf<DiaryModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<DiaryModel> {
        logAll()
        return entries
    }

    override fun create(entry: DiaryModel) {
        entry.id = generateRandomId()
        entries.add(entry)
        serialize()
    }


    override fun update(entry: DiaryModel) {
        val entriesList = findAll() as ArrayList<DiaryModel>
        var foundEntry: DiaryModel? = entriesList.find { p -> p.id == entry.id }
        if (foundEntry != null) {
            foundEntry.title = entry.title
            foundEntry.description = entry.description
            foundEntry.image = entry.image
        }
        serialize()
    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(entries, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        entries = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(entry: DiaryModel) {
        entries.remove(entry)
        serialize()
    }



    private fun logAll() {
        entries.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}