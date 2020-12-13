import java.awt.Color
import java.time.*
import myaa.subkt.ass.EventLineAccessor
import myaa.subkt.tasks.*
import myaa.subkt.tasks.Mux.*
import myaa.subkt.tasks.Nyaa.*

plugins {
    id("myaa.subkt")
}

subs {
    readProperties("sub.properties")
    episodes(getList("episodes"))

    merge {
        from(get("dialogue"))

        from(get("ED1"))
        from(get("ED2"))

        from(get("INS1"))
        from(get("INS2"))

        from(get("TS1"))
        from(get("TS2"))

        out(get("mergedname"))
    }

    chapters {
        from(merge.item())
        chapterMarker("chapter")
    }


    mux {
        title(get("title"))

        from(get("premux")) {
            includeChapters(false)
			attachments { include(false) }
        }

		from(merge.item()) {
			tracks {
				lang("eng")
                name(get("group"))
				default(true)
				forced(false)
				compression(CompressionType.ZLIB)
			}
		}

        chapters(chapters.item()) {
            lang("eng")
        }

        attach(get("fonts")) {
            includeExtensions("ttf", "otf")
        }

        attach(get("songfonts")) {
            includeExtensions("ttf", "otf")
        }

        out(get("muxout"))
    }
}
