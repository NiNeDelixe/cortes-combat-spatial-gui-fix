plugins {
	id("mod-platform")
	id("net.neoforged.moddev")
}

stonecutter {
	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
}

platform {
	loader = "neoforge"
	dependencies {
		required("minecraft") {
			forgeLikeVersionRange = prop("deps.minecraft")
		}
		required("neoforge") {
			forgeLikeVersionRange.set("[${prop("deps.neoforge")},)")
		}
		if (sc.current.version == "1.21.1") {
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

neoForge {
	version = prop("deps.neoforge")
	accessTransformers.from(rootProject.file("src/main/resources/aw/${stonecutter.current.version}.cfg"))
	validateAccessTransformers = true

	if (sc.properties.contains("deps.parchment")) parchment {
		val (mc, ver) = prop("deps.parchment").split(':')
		mappingsVersion = ver
		minecraftVersion = mc
	}

	runs {
		val devJvmArgs = propsList("mod", "dev_jvm_args") +
			propsList("mod", "dev_jvm_args_mixin_debug")
		register("client") {
			client()
			gameDirectory = file("run/")
			ideName = "NeoForge Client (${stonecutter.current.version})"
			programArgument("--username=Dev")
			jvmArguments.addAll(devJvmArgs)
		}
		register("server") {
			server()
			gameDirectory = file("run/")
			ideName = "NeoForge Server (${stonecutter.current.version})"
			jvmArguments.addAll(devJvmArgs)
		}
	}

	mods {
		register(prop("mod.id")) {
			sourceSet(sourceSets["main"])
		}
	}
	sourceSets["main"].resources.srcDir("${rootDir}/versions/datagen/${sc.current.version}/src/main/generated")
}

repositories {
	mavenCentral()
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
	if (sc.current.version == "1.21.1") {
		ivy("https://github.com/CerebroCanibalus/cortes-combat/releases/download") {
			patternLayout { artifact("v[revision]/[artifact]-[revision]-[classifier].[ext]") }
			metadataSources { artifact() }
			content { includeModule("com.cortes.combat", "cortescombat") }
		}
	}
}

dependencies {
	if (sc.current.version == "1.21.1") {
		implementation("com.cortes.combat:cortescombat:${prop("deps.cortescombat")}:${sc.current.version}@jar")
		implementation("maven.modrinth:spatial-gui:${prop("deps.spatial-gui")}")
		runtimeOnly("maven.modrinth:yacl:${prop("deps.yacl")}")
	}
	// implementation(libs.moulberry.mixinconstraints)
	// jarJar(libs.moulberry.mixinconstraints)
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(tasks.named("stonecutterGenerate"))
}
