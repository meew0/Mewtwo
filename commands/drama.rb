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
replace = config['drama']['replace']
user = ARGV[2] ? "#{ARGV[2]}, " : ''

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

  drama = open("http://asie.pl/drama.php?plain").read
  drama.sub!(%r{m[^mew0]*e[^mew0]*e[^mew0]*w[^mew0]*0}, new_people.sample)

  puts "#{user}#{drama}"

end
