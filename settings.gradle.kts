rootProject.name = "LifeStealZ"

sequenceOf(
    // "paper",
    "bukkit"
).forEach {
    include("${rootProject.name}-$it")
    project(":${rootProject.name}-$it").projectDir = file(it)
}