plugins {
	id("mod-platform")
	id("net.neoforged.moddev.legacyforge")
}

stonecutter {
	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
}

platform {
	loader = "forge"
	dependencies {
		required("minecraft") {
			forgeLikeVersionRange = prop("deps.minecraft")
		}
		required("forge") {
			forgeLikeVersionRange.set("[${prop("deps.forge")},)")
		}
		if (sc.current.version == "1.20.1") {
			required("cortescombat") {
				forgeLikeVersionRange = "[${prop("deps.cortescombat")},)"
				environment = "client"
			}
			required("spatial_gui") {
				slug("spatial-gui")
				forgeLikeVersionRange = "[1.11.0,)"
				environment = "client"
			}
		}
	}
}

legacyForge {
	version = "${prop("deps.minecraft")}-${prop("deps.forge")}"

	validateAccessTransformers = true

	accessTransformers.from(
		rootProject.file("src/main/resources/aw/${sc.current.version}.cfg")
	)

	val devJvmArgs = propsList("mod", "dev_jvm_args") +
		propsList("mod", "dev_jvm_args_mixin_debug")
	runs {
		register("client") {
			client()
			gameDirectory = file("run/")
			ideName = "Forge Client (${sc.current.version})"
			programArgument("--username=Dev")
			jvmArguments.addAll(devJvmArgs)
		}
		register("server") {
			server()
			gameDirectory = file("run/")
			ideName = "Forge Server (${sc.current.version})"
			jvmArguments.addAll(devJvmArgs)
		}
	}


	mods {
		register(prop("mod.id")) {
			sourceSet(sourceSets["main"])
		}
	}
}

mixin {
	add(sourceSets.main.get(), "${prop("mod.id")}.mixins.refmap.json")
	config("${prop("mod.id")}.mixins.json")
}

repositories {
	mavenCentral()
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
	if (sc.current.version == "1.20.1") {
		ivy("https://github.com/CerebroCanibalus/cortes-combat/releases/download") {
			patternLayout { artifact("v[revision]/[artifact]-[revision]-[classifier].[ext]") }
			metadataSources { artifact() }
			content { includeModule("com.cortes.combat", "cortescombat") }
		}
	}
}

dependencies {
	annotationProcessor("org.spongepowered:mixin:${libs.versions.mixin.get()}:processor")
	if (sc.current.version == "1.20.1") {
		"modImplementation"("com.cortes.combat:cortescombat:${prop("deps.cortescombat")}:${sc.current.version}@jar")
		"modImplementation"("maven.modrinth:spatial-gui:${prop("deps.spatial-gui")}")
		"modRuntimeOnly"("maven.modrinth:yacl:${prop("deps.yacl")}")
	}

	// implementation(libs.moulberry.mixinconstraints)
	// jarJar(libs.moulberry.mixinconstraints)
}

sourceSets {
	main {
		resources.srcDir(
			"${rootDir}/versions/datagen/${sc.current.version}/src/main/generated"
		)
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(tasks.named("stonecutterGenerate"))
}
