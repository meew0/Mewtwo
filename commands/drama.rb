require 'open-uri'
require 'parseconfig'

static_dramas = []

def to_boolean(str)
  str == 'true'
end

new_people = ["Player", "jadedcat", "Alblaka", "Greg", "Eloraam",
              "AUTOMATIC_MAIDEN", "Alz454",
              "RS485", "Shukaro", "Toops", "CovertJaguar", "Pahimar", "Sengir",
              "Azanor", "jeb", "Greymerk",
              "Dinnerbone", "Grum", "dan200", "Cloudy", "KingLemming", "Zeldo",
              "AlgorithmX2", "Mikee", "Eyamaz",
              "kakermix", "cpw", "LexManos", "Vswe", "Direwolf20", "Calclavia",
              "Reika", "Sangar", "skyboy",
              "FlowerChild", "SpaceToad", "ChickenBones", "Notch", "Pokefenn",
              "Shadowclaimer", "Vazkii", "pixlepix",
              "nekosune", "copygirl", "immibis", "RichardG", "JAKJ", "mDiyo",
              "pillbox", "progwml6", "PowerCrystals",
              "GUIpsp", "nallar", "Soaryn", "Soaryn", "AbrarSyed", "Sunstrike",
              "BevoLJ", "asie", "tterrag",
              "CrazyPants", "Aidan", "Binnie", "Mojang", "ProfMobius",
              "peterix", "RWTema", "Slowpoke", "Curse",
              "bspkrs", "Mr_okushama", "Searge", "iChun", "Krapht",
              "Erasmus_Crowley", "MysteriousAges", "Drullkus",
              "Micdoodle8","GenPage","Hunterz","tgame14", "Velotican",
              "kirindave", "MachineMuse", "M3gaFr3ak",
              "Lunatrius", "wha-ha-ha", "MamiyaOtaru"]

config = ParseConfig.new('commands.cfg')
staticmode = to_boolean(config['drama']['staticmode'])
selfgen = to_boolean(config['drama']['selfgen'])
newurl = to_boolean(config['drama']['newurl'])
replace = config['drama']['replace']
user = '' #ARGV[2] ? "#{ARGV[2]}, " : ''

selfgen = true if ARGV[2]

if selfgen
  msg = [
  	"[people] launched a DoS attack on the website of [things]",
  	#"[sites] urges everyone to stop using [things]",
  	#"After a [enormous] amount of requests, [packs] removes [things]",
  	#"After a [enormous] amount of requests, [packs] adds [things]",
  	#"After a [enormous] amount of requests, [packs] adds [function] to [things]",
  	"[people] plays [things] on Twitch",
  	"[people] fixes [function] in [things] to be unlike [things]",
  	#"[things] makes [things] [crash], [sites] users complain",
  	"[people] complained about being in [things] on [sites]",
  	"[people] releases [code] of [things] for [price]",
  	#"[sites] considers [things] worse than [things]",
  	"[people] made [things] depend on [things]",
  	"[people] bans [people] from using [things] in [packs]",
  	"[people] complains that [things] discussion doesn't belong on [sites]",
  	"[people] has a Patreon goal to add [function] to [things] for [price] a month",
  	"[people] has a Patreon goal to add [things] compatibility to [things] for [price] a month",
  	"[people] complains that [people] replaced [things] by [things]",
  	"[people] complains that [people] replaced [things] by [things] in [packs]",
  	"[people] complains that [people] removed [function] in [packs]",
  	"[people] decided that [things] is too [adj] and replaced it with [things]",
  	"[people] [says] [things] is [adj].",
  	"[people] [says] [things] is literally [adj].",
  	#"[things] is not updated for the latest version of Minecraft.",
  	"[people] removes [things] from [packs].",
  	"[people] adds [things] to [packs].",
  	"[people] quits modding. Fans of [things] rage.",
  	"[people] is found to secretly like [things]",
  	"[people] openly hates [function] in [things]",
  	"[people] threatens to [ac1] [people] until they remove [things] from [packs]",
  	"[people] threatens to [ac1] [people] until they remove [function] from [things]",
  	"[people] threatens to [ac1] [people] until they add [function] to [things]",
  	"[people] came out in support of [things]",
  	"[people] came out in support of [drama]",
  	"[people] and [people] came out in support of [drama]",
  	"[people] came out against [drama], [sites] rages",
  	"[people] and [people] came out against [drama], [sites] rages",
  	"[people] forks [things] causing [drama]",
  	"[people] [says] to replace [things] with [things]",
  	"[people] [says] [people] causes drama",
  	#"[things] fans claim that [things] should be more like [things]",
  	#"[things] fans claim that [things] should have better [function]",
  	"[people] [says] that [things] should be more like [things]",
  	"[people] [says] that [things] should be less like [things]",
  	"[people] rebalances [things] for [packs]",
  	"[people] adds [function] to [things] by request of [people]",
  	"[people] removes [function] from [things] by request of [people]",
  	"[people] removes compatibility between [things] and [things] by request of [people]",
  	"[people] [says] [people]'s attitude is [adj]",
  	"[people] [says] [sites]'s attitude is [adj]",
  	"[people] quits the development team of [things]",
  	"[people] [says] [things] is too much like [things]",
  	"[people] [says] [things] is a ripoff of [things]",
  	"[people] [says] [people] stole [code] from [people]",
  	"[people] [says] [people] did not steal [code] from [people]",
  	"[people] decides to [ban] [people] from [packs]",
  	#"[things] doesn't work with [things] since the latest update",
  	"[people] sues [things]",
  	"[people] [says] [things] is [adj] on [sites]",
  	"[people] [says] [things] is full of [badsoft]",
  	"[people] [says] [things] causes [drama]",
  	"[people] [says] [things] causes [drama] when used with [things]",
  	"[people] [says] using [things] and [things] together is [adj]",
  	"[people] rants about [things] on [sites]",
  	"[people] rants about [function] in mods on [sites]",
  	"[people] steals code from [things]",
  	#"[things] breaks [function]",
  	"[people] sues [things] developers",
  	"[people] reminds you that [things] is [adj]",
  	"[people] and [people] get into a drama fight on [sites]",
  	#"Fans of [things] and [things] argue on [sites]",
  	"[people] and [people] argue about [things]",
  	"[people] puts [badsoft] in [things]",
  	"[people] complains about [things] breaking [things]",
  	"[people] complains about [things] breaking [function]",
  	"[people] complains about [things] including [function]",
  	#"[things] breaks [function] in [things]",
  	#"[things] breaks [things] support in [things]",
  	#"[things] adds code to [ban] [people] automatically",
  	#"[things] adds code to [ban] people using [things]",
  	#"[things] removes compatibility with [things]",
  	"[people] [says] not to use [things]",
  	"[people] [says] not to use [things] with [things]",
  	"[people] finds [badsoft] in [things]",
  	"[people] drew a nasty graffiti about [people]",
  	"[people] drew a nasty graffiti about [things]",
  	#"[things] makes [things] [crash] when used with [things]",
  	"[things] makes [things] [crash] when used by [people]",
  	"[things] makes [things] crash [things] when used by [people]",
  	#"[things] adds [badsoft] that only [activates] in [packs]",
  	#"[things] adds [badsoft] that only [activates] alongside [things]",
  	"[things] makes [people] invincible from [things] in [packs]",
  	"[people] decides to base their entire modpack on [things]",
  	"[people] tweaks balance in [things] too much, annoying [sites]",
  	"[people] tweaks balance in [things] too much, annoying [people]",
  	"[people] [says] [people] is worse than [people]",
  	"[people] [says] [things] is [worse] than [things]",
  	"[people] bans [people] from [sites]"
  ].sample

  people_array = ["Player", "jadedcat", "Alblaka", "Greg", "Eloraam", "AUTOMATIC_MAIDEN", "Alz454", "RS485", "Shukaro", "Toops", "CovertJaguar", "Pahimar", "Sengir", "Azanor", "jeb", "Greymerk", "Dinnerbone", "Grum",
    "dan200", "Cloudy", "KingLemming", "Zeldo", "AlgorithmX2", "Mikee", "Eyamaz", "kakermix", "cpw", "LexManos",
    "Vswe", "Direwolf20", "Calclavia", "Reika", "Sangar", "skyboy", "FlowerChild", "SpaceToad", "ChickenBones",
    "Notch", "Pokefenn", "Shadowclaimer", "Vazkii", "pixlepix", "nekosune", "copygirl", "immibis", "RichardG",
    "JAKJ", "mDiyo", "pillbox", "progwml6", "PowerCrystals", "GUIpsp", "nallar", "Soaryn", "Soaryn", "AbrarSyed",
    "Sunstrike", "BevoLJ", "asie", "tterrag", "CrazyPants", "Aidan", "Binnie", "Mojang", "ProfMobius", "peterix",
    "RWTema", "Slowpoke", "Curse", "bspkrs", "Mr_okushama", "Searge", "iChun", "Krapht", "Erasmus_Crowley", "MysteriousAges",
    "Drullkus", "Micdoodle8","GenPage","Hunterz","tgame14", "Velotican", "kirindave", "MachineMuse", "M3gaFr3ak", "Lunatrius", "wha-ha-ha"]
  people_array = [(ARGV[2..-1] * ' ')] if ARGV[2..-1]

  {
    "people" => people_array,
    "sites" => ["FTB Forums", "MCF", "Reddit", "4chan", "Technic Forums", "IC2 Forums", "GitHub", "BitBucket", "IRC", "ForgeCraft", "Patreon", "BTW Forums", "GregTech thread", "Google+", "Twitch"],
  	"things" => ["ForgeCraft", "Simply Jetpacks", "RedPower 2", "ModLoader", "RedLogic", "Forge MultiPart", "Project: Red", "BuildCraft", "Tinkers' Steelworks", "Artifice", "Roguelike Dungeons", "IndustrialCraft 2", "Equivalent Exchange", "Forestry", "RailCraft", "Simple Jetpacks", "Compact Solars", "ComputerCraft", "Wireless Redstone", "OpenComputers", "GregTech", "Ars Magica", "Thaumcraft", "FTB", "Technic", "Resonant Rise", "MineFactory Reloaded", "Magic Farm 2", "Tekkit", "MCPC+", "ATLauncher", "Metallurgy", "Logistics Pipes", "MCUpdater", "MultiMC", "Curse", "Mojang", "Test Pack Please Ignore", "Agrarian Skies", "Steve's Carts", "Steve's Factory Manager", "BiblioCraft",  "Minecraft", "XyCraft", "Forge", "GregTech", "OpenBlocks", "OpenPeripheral", "OpenComputers", "MFFS", "RotaryCraft", "Big Reactors", "Thermal Expansion 3", "Extra Utilities", "Universal Electricity", "Not Enough Items", "Portal Gun mod", "the Mojang launcher", "Too Many Items", "OptiFine", "Extra Cells", "ExtraBiomesXL", "Biomes O' Plenty", "Better than Wolves", "Schematica", "Tinker's Construct", "Natura", "Hexxit", "Iron Chests", "open-source mods", "closed-source mods", "Not Enough Mods", "Ender IO", "Mekanism", "Minecraft 1.7", "Pixelmon", "Pixelmon", "JABBA", "WAILA", "Opis", "CraftGuide", "Iguana Tweaks", "Tinkers Mechworks", "the Minecraft Drama Generator", "MineChem", "LittleMaidMob", "MCP", "Immibis' Microblocks", "Carpenter's Blocks", "Chisel", "Applied Energistics", "Applied Energistics 2", "Rotatable Blocks", "EnhancedPortals 3", "Ex Nihilo", "Ex Aliquo", "Magic Bees", "BetterStorage", "Backpacks", "Aether II", "Highlands", "Alternate Terrain Generation", "InfiCraft", "Bukkit", "Spigot", "SpoutCraft", "MortTech", "ICBM", "Galacticraft", "Modular Power Suits", "Team CoFH", "Extra Bees", "Extra Trees", "Mo' Creatures", "Grimoire of Gaia", "Atum", "Agriculture", "Sync", "Hats", "Nether Ores"],
  	"packs" => ["Feed The Beast", "the ForgeCraft pack", "FTB Monster", "FTB Unstable", "Agrarian Skies", "Direwolf20 Pack", "Tekkit", "Hexxit", "ATLauncher", "Resonant Rise", "MCUpdater", "Attack of the B-Team", "Mindcrack", "Magic Maiden", "ForgeCraft", "Technic"],
  	"function" => ["MJ support", "RF support", "EU support", "FMP compatibility", "quarries", "automatic mining", "GregTech balance", "ComputerCraft APIs", "OpenComputers APIs", "Bukkit plugin compatibility", "MCPC+ support", "ID allocation", "ore processing", "smelting", "crafting", "balance", "bees", "ThaumCraft integration", "realism", "decorative blocks", "new mobs", "TCon tool parts", "new wood types", "bundled cable support", "new player capes", "more drama", "less drama", "microblocks", "drama generation commands", "Blutricity support", "overpowered items", "underpowered items", "new ores", "better SMP support", "achievements", "quests", "more annoying worldgen"],
  	"adj" => ["bad", "wrong", "illegal", "horrible", "nasty", "not in ForgeCraft", "noncompliant with Mojang's EULA", "a serious problem", "incompatible", "a waste of time", "wonderful", "amazing", "toxic", "too vanilla", "shameful", "disappointing", "bloated", "outdated", "incorrect", "full of drama", "too realistic"],
  	"badsoft" => ["malware", "spyware", "adware", "DRM", "viruses", "trojans", "keyloggers", "stolen code", "easter eggs", "potential login stealers", "adf.ly links", "bad code", "stolen assets", "malicious code", "secret backdoors"],
  	"drama" => ["bugs", "crashes", "drama", "lots of drama", "imbalance", "pain and suffering", "piracy", "bees", "adf.ly"],
  	"crash" => ["crash", "explode", "break", "lag", "blow up", "corrupt chunks", "corrupt worlds", "rain hellfish", "spawn bees"],
  	"ban" => ["ban", "kick", "put a pumpkin of shame on", "add items mocking", "blacklist", "whitelist", "give admin rights to", "shame", "destroy"],
  	"code" => ["code", "assets", "ideas", "concepts", "a single function", "5 lines of code", "a class", "a few files", "a ZIP file", "Gradle buildscripts", "a GitHub repository"],
  	"worse" => ["worse", "better", "faster", "slower", "more stable", "less buggy"],
  	"ac1" => ["sue", "destroy the life of", "flame", "cause drama about", "complain about", "kick"],
  	"price" => ["200$", "250$", "300$", "350$", "400$", "450$", "500$", "600$"],
  	"activates" => ["activates", "works", "functions", "breaks"],
  	"says" => ["says", "tweets", "claims", "confirms", "denies"],
    "enormous" => ["big", "large", "huge", "gigantic", "enormous"]
  }.each { |k,v|
    msg.gsub!("[#{k}]").each { |e|
      e = v.sample
    }
  }

  puts "#{user}#{msg}"
else


  if staticmode
    static_dramas = [
      'Pixelmon fans claim that Agrarian Skies should have better armor',
      "Erasmus_Crowley complains that Eloraam replaced Rei's Minimap by Rei's " \
      'Minimap in MCUpdater',
      'MCPC+ breaks Blutricity support in RailCraft',
      'RWTema releases 5 lines of code of FTB for one million adf.ly download' \
      's',
      'LexManos says CraftGuide is full of stolen code',
      'Soaryn complains about closed-source mods including overpowered items',
      'CrazyPants steals code from Hypixel',
      "tterrag removes compatibility between Tinkers' Steelworks and BuildCraft " \
      'by request of JAKJ',
      'ProfMobius decides to base their entire modpack on MCPC+',
      "wha-ha-ha complains about Binnie's big profit from Flattr",
      'CrazyPants openly hates decorative blocks in Simply Jetpacks',
      "CanVox steals code from Tinkers' Steelworks",
      "dan200 confirms Greg's attitude is incompatible",
      'tgame14 threatens to sue mDiyo until they remove EnhancedPortals 3 from ' \
      'Patreon',
      'Cloudy decided that OpenPeripheral is too a serious problem and replaced ' \
      'it with Simple Jetpacks',
      'Fans of Extra Cells and RedLogic argue on Pastebin',
      "Modular Power Suits doesn't work with MineFactory Reloaded since the late" \
      'st update',
      'MamiyaOtaru sues ExtraBiomesXL',
      'Greymerk rebalances XyCraft for the ForgeCraft pack',
      "OpenComputers fans claim that Extra Bees should be more like Tinkers' Ste" \
      'elworks',
      'Opis adds viruses that only breaks in Minecraft 1.7.2',
      'MineFactory Reloaded adds easter eggs that only works alongside Simply' \
      ' Jetpacks',
      'Cloudy removes quarries from BetterStorage by request of nekosune',
      'KingLemming threatens to cause drama about Shadowclaimer until they re' \
      'move ATLauncher from CurseForge',
      'RichardG rants about closed-source mods on Technic Forums',
      'Forge MultiPart makes Ex Nihilo corrupt worlds when used with BuildCra' \
      'ft',
      'Reika says ProfMobius causes drama',
      'Hypixel adds code to give admin rights to people forking Minecraft 1.7' \
      '',
      'After a mesmerizing amount of requests, Mindcrack adds bundled cable s' \
      'upport to Minecraft 1.7',
      'wha-ha-ha complains that iChun replaced Applied Energistics 2 by Not E' \
      'nough Mods in ATLauncher',
      'Grum adds MCUpdater to Hexxit',
      'Hypixel adds code to shame people forking ATLauncher',
      'LexManos came out in support of Tinkers Mechworks',
      'Mr_okushama denies Calclavia is worse than Drullkus',
      'Sengir came out in support of drama',
      'open-source mods is not updated for the latest version of Minecraft',
      'RedPower 2 adds code to ban people forking Project: Red',
      'CovertJaguar and pixlepix came out in support of crashes',
      'copygirl threatens to flame Zeldo until they remove Curse from adfoc.u' \
      's',
      'KingLemming steals code from WAILA',
      'RedPower 3 breaks Bukkit plugin compatibility',
      'Hexxit makes CanVox invincible from Project: Red in Minecraft 1.2.5',
      'Resonant Rise is not updated for the latest version of Minecraft',
      'Sunstrike tweets Hexxit is a ripoff of XyCraft',
      "Sunstrike complains that Hypixel discussion doesn't belong on IRC",

      'wha-ha-ha claims Grum causes drama',
      'kirindave adds ComputerCraft APIs to Extra Bees by request of Micdoodl' \
      'e8',
      'tterrag came out in support of pain and suffering',
      'Shadowclaimer denies Cloudy stole 5 lines of code from SpaceToad',
      'Searge drew a nasty graffiti about Alz454',
      'jadedcat uploads logs of GUIpsp talking about WAILA to 4chan',
      'SpaceToad made Roguelike Dungeons depend on RailCraft',
      'Backpacks makes dan200 invincible from Applied Energistics in FTB Mons' \
      'ter',
      'RS485 tweets tweaking GregTech and IC2 Classic together is amazing',
      'RWTema drew a nasty graffiti about MultiMC',
      'bspkrs complains that Drullkus replaced OpenComputers by Bukkit',
      'Alblaka uploads logs of Cloudy talking to Alz454 to the Minecraft gene' \
      'ral',
      'After a surprisingly big amount of requests, Minecraft 1.7.2 removes B' \
      'iblioCraft',
      'immibis decides to blacklist ChickenBones from Direwolf20 Pack',
      'RWTema threatens to sue tterrag until they remove Portal Gun mod from ' \
      'Flattr',
      'LexManos confirms Dinnerbone causes drama',
      'RotaryCraft makes Spigot lag when used by Pahimar',
      'the Mojang blog considers ForgeCraft worse than MortTech',
      'Sengir says not to use Nether Ores',
      'Hats fans claim that OpenPeripheral should be more like Backpacks',
      'Fans of MFFS and Mekanism argue on Technic Forums',
      'nallar says to replace Too Many Items with ICBM',
      "Curse complains about nekosune's surprisingly large profit from CurseForge",
      "jadedcat claims that Extra Utilities should be more like Immibis' Microb" \
      'locks',
      'Soaryn claims Forestry is too much like OptiFine',
      'After a surprisingly big amount of requests, Technic adds smelting to ' \
      'Modular Power Suits',
      'After a shocking amount of requests, MCUpdater removes EU support from' \
      ' Portal Gun mod',
      'Soaryn came out in support of Agrarian Skies',
      "Lunatrius complains that Alz454 replaced Steve's Factory Manager by Chisel",
      'Krapht forks Metallurgy causing adf.ly',
      'skyboy quits the development team of Spigot',
      'Vazkii decides to destroy jeb from ATLauncher',
      'Eyamaz releases a ZIP file of Simple Jetpacks for 200$',
      'Simply Jetpacks adds adf.ly links that only works alongside Compact So' \
      'lars',
      'Modular Power Suits makes Extra Cells crash Aether II when used by RWT' \
      'ema',
      'Calclavia claims Project: Red is too much like MultiMC',
      'Extra Trees adds DRM that only activates alongside EnhancedPortals 3',
      'Galacticraft breaks Simply Jetpacks support in GregTech',
      'RWTema adds Ars Magica to Tekkit',
      'jeb adds ID allocation to Project: Red by request of SpaceToad',
      'Direwolf20 uploads logs of M3gaFr3ak talking to Erasmus_Crowley to BTW' \
      ' Forums',
      'After a big amount of requests, FTB Unstable removes GregTech balance ' \
      "from Immibis' Microblocks",
      'Slowpoke complains that Notch prefer RedPower 3 to Big Reactors',
      'bspkrs confirms not to use ModLoader',
      'Shukaro sues Bukkit',
      'Dinnerbone complains that GUIpsp removed new player capes in Minecraft' \
      ' 1.6.4',
      'Azanor puts potential login stealers in Roguelike Dungeons',
      'Direwolf20 denies Sync causes lots of drama when used with Applied Ene' \
      'rgistics',
      'Eloraam adds compatibility between Grimoire of Gaia and Backpacks by r' \
      'equest of RS485',
      'Greymerk rants about MCUpdater on Twitch',
      'AbrarSyed claims that RailCraft should be more like Project: Red',
      'IC2 Classic breaks new mobs',
      'Admins of survival servers think forking Forge MultiPart is bad',
      'Mikee plays BuildCraft on Twitch',
      "Rei's Minimap adds viruses that only functions alongside Grimoire of Gaia",

      'FTB fans claim that Compact Solars should be more like Artifice',
      'GenPage decided that Minecraft is too incorrect and replaced it with C' \
      "arpenter's Blocks",

      'After a large amount of requests, Direwolf20 Pack removes EU support f' \
      'rom ComputerCraft',
      'AbrarSyed confirms Ex Nihilo is too realistic',
      "Tinkers' Steelworks breaks Rotatable Blocks support in InfiCraft",

      'AbrarSyed denies Hats is wonderful',
      'Admins of modded servers think using InfiCraft is wonderful',
      "skyboy came out in support of Steve's Carts",

      'AlgorithmX2 quits modding. Fans of Hypixel rage',
      'Grum decides to blacklist Player from vanilla',
      'IC2 Classic fans claim that RedPower 2 should have better OpenComputer' \
      's APIs',
      'bspkrs adds compatibility between Modular Power Suits and ComputerCraf' \
      't by request of Erasmus_Crowley',
      'kakermix threatens to destroy the life of Lunatrius until they remove ' \
      'Minecraft 1.7 from adf.ly',
      'GenPage releases concepts of Agriculture for 250$',
      'AbrarSyed rebalances Resonant Rise for vanilla',
      'Shukaro rants about GregTech on IC2 Forums',
      'RS485 says jeb is worse than Zeldo',
      'Curse threatens to flame MysteriousAges until they add automatic minin' \
      'g to Magic Bees',
      "Agrarian Skies doesn't work with OpenComputers since the latest update",

      "Alz454 says Google+'s attitude is wrong",

      'AUTOMATIC_MAIDEN complains that nallar replaced Forge MultiPart by Rog' \
      'uelike Dungeons',
      "Toops came out in support of Mo' Creatures",

      'FlowerChild sues Simply Jetpacks developers',
      'Binnie tweets Hats is literally wonderful',
      'Eloraam claims editing the Minecraft Drama Generator and MultiMC toget' \
      'her is full of drama',
      'dan200 uploads logs of AUTOMATIC_MAIDEN talking to Notch to the Minecr' \
      'aft general',
      "ChickenBones forks Steve's Factory Manager causing imbalance",

      'M3gaFr3ak quits modding. Fans of OptiFine rage',
      "CovertJaguar confirms IC2 Forums's attitude is amazing",

      'kirindave complains about BiblioCraft breaking more drama',
      'Vswe confirms Curse is better than ComputerCraft',
      'M3gaFr3ak tweets stealing Not Enough Mods and RedPower 2 together is a' \
      ' serious problem',
      'jeb says MCUpdater is less buggy than Magic Bees',
      'RedLogic makes Alternate Terrain Generation spawn bees, Technic Forums' \
      ' users complain',
      'MysteriousAges made Botania depend on Resonant Rise',
      'IC2 Classic adds adware that only works in Minecraft 1.2.5',
      "MamiyaOtaru confirms Eloraam's attitude is nasty",

      'kakermix tweaks balance in Equivalent Exchange too much, annoying RWTe' \
      'ma',
      'Grimoire of Gaia makes Not Enough Items rain hellfish when used with R' \
      'otatable Blocks',
      "Tinkers Mechworks fans claim that Forestry should be more like Steve's" \
      'Factory Manager',

      'Eyamaz made Portal Gun mod depend on RailCraft',
      'pillbox quits modding. Fans of open-source mods rage',
      'kakermix openly hates TCon tool parts in open-source mods',
      'EnhancedPortals 3 adds keyloggers that only activates in Minecraft 1.2' \
      '.5',
      'Ars Magica breaks automatic mining',
      'CanVox claims MamiyaOtaru stole a GitHub repository from MamiyaOtaru',
      'Zeldo tweets Thaumcraft is more stable than ExtraBiomesXL',
      'ProfMobius drew a nasty graffiti about Nether Ores',
      'Shukaro sues MortTech',
      'KingLemming and pixlepix came out in support of pain and suffering',
      'Extra Bees fans claim that Mekanism should be more like Tinkers Mechwo' \
      'rks',
      'MamiyaOtaru complained about being in Bukkit on FTB Forums',
      'kakermix rebalances Technic for Technic',
      'Simply Jetpacks breaks armor',
      'mDiyo tweaks balance in RedPower 2 too much, annoying Curse',
      'Spigot adds secret backdoors that only breaks in ForgeCraft',
      'Greymerk complains about Artifice breaking Ender IO',
      'kirindave denies not to use IndustrialCraft 2',
      'Dinnerbone releases code of EnhancedPortals 3 for 450$',
      'Pokefenn sues BiblioCraft',
      'Mojang decided that BuildCraft is too bad and replaced it with MortTec' \
      'h',
      "tgame14 confirms 4chan's attitude is disappointing",

      'GenPage sues Bukkit',
      'After a surprisingly small amount of requests, Direwolf20 Pack adds Co' \
      'mpact Solars',
      'skyboy denies OpenPeripheral is incompatible',
      'Alz454 complains that Erasmus_Crowley replaced FTB by Project: Red',
      'Natura is not updated for the latest version of Minecraft',
      'cpw came out in support of imbalance',
      "Curse complains about MamiyaOtaru's large profit from CurseForge",

      'Patreon considers Backpacks worse than Forge MultiPart',
      'AUTOMATIC_MAIDEN came out against pain and suffering, IC2 Forums rages' \
      '',
      'MamiyaOtaru made Hexxit depend on MultiMC',
      'kakermix decides to add items mocking RWTema from vanilla',
      'BevoLJ reminds you that MFFS is bad',
      'BuildCraft makes tgame14 invincible from Modular Power Suits in MCUpda' \
      'ter',
      'Grum tweets not to use MineFactory Reloaded with Equivalent Exchange',
      'LexManos came out in support of drama',
      'dan200 denies editing Magic Farm 2 and CraftGuide together is not in F' \
      'orgeCraft',
      'ATLauncher breaks GregTech balance in InfiCraft',
      'MamiyaOtaru tweets MCP is bloated',
      'kirindave decides to base their entire modpack on EnhancedPortals 3',
      "mDiyo complains that Biomes O' Plenty discussion doesn't belong on the M" \
      'inecraft general',

      'SpaceToad fixes TCon tool parts in Alternate Terrain Generation to be ' \
      'unlike FTB',
      'pillbox complains that RichardG removed more drama in Direwolf20 Pack',
      "Biomes O' Plenty makes Iron Chests crash when used with Equivalent Exc" \
      'hange',

      'Artifice breaks bees',
      'Soaryn complains about JABBA breaking realism',
      'Greymerk quits the development team of Metallurgy',
      'Rotatable Blocks adds code to destroy people tweaking closed-source mo' \
      'ds',
      'mDiyo tweets SpoutCraft is a ripoff of Artifice',
      'CanVox complained about being in Test Pack Please Ignore on 4chan',
      'Mojang threatens to kick Vswe until they remove more annoying worldgen' \
      ' from open-source mods',
      'Shukaro threatens to complain about Alz454 until they remove Iguana Tw' \
      'eaks from Attack of the B-Team',
      'Toops tweaks balance in open-source mods too much, annoying Player',
      'Shukaro came out in support of bees',
      'Hats removes compatibility with Natura',
      'asie tweets closed-source mods is full of potential login stealers',
      'After a huge amount of requests, Resonant Rise removes RF support from' \
      ' Applied Energistics 2',
      'Krapht denies Cloudy did not steal Gradle buildscripts from asie',
      'BevoLJ drew a nasty graffiti about RotaryCraft'
    ]

    drama = static_dramas.sample
    puts "#{user}#{drama}"
  else
    url = "http://asie.pl/drama.php?plain"
    url += "&2" if newurl
    drama = open(url).read
    drama.sub!(%r{m[^mew0]*e[^mew0]*e[^mew0]*w[^mew0]*0}, new_people.sample)

    drama = drama[0..drama.index('<')-1] if newurl

    puts "#{drama}"

  end
end
