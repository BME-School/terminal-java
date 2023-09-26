<code>

    pwd: kiírja az aktuális könyvtár (wd) elérési útját (getCanonicalPath())

    cd <dir>: az aktuális könyvtárból átlép a benne levő, <dir> nevű alkönyvtárba. Ha
    <dir> értéke "..", akkor egy szinttel feljebb lép (getParentFile()).
    Ha a <dir> nem létező könyvtár, akkor írjon ki hibaüzenetet! A parancs fogja-e módosítani a wd értékét?

    ls: javított listázás, amely kilistázza az aktuális könyvtárban levő fájlok és könyvtárak neveit, de van egy lehetséges paramétere:
        o -l: listáz, mint eddig, de a listában megjeleníti a fájlok méretét és típusát is (d - könyvtár, f - sima fájl)

    rm <file>: törli a <file> nevű fájlt. (delete()) Ha probléma merül fel, akkor adjon hibajelzést

    mkdir <dir>: létrehozza az aktuális könyvtárban a <dir> nevű könyvtárat (mkdir())
    Ha <dir> már létezik, írjon ki hibaüzenetet!

    mv <file1> <file2>: <file1> fájlt átnevezi <file2>-re (renameTo())\
    Ha hiba történt, jelezze!

    cp <file1> <file2>: <file1>-et átmásolja <file2>-be. Használja a\
    FileInputStream és FileOutputStream osztályokat, és bájtonként másolja át a tartalmat. Ha ideje engedi, próbáljon blokkos másolást1.\
    Ha a fájl nem létezik, adjon hibajelzést!

    cat <file>: kiírja a <file> nevű fájl tartalmát soronként a szabványos kimenetre. Használjon FileReadert és BufferedReadert!\
    Ha a fájl nem létezik, adjon hibajelzést!

    length <file>: kiírja a <file> nevű fájl hosszát. Ha a fájl nem létezik, adjon hibajelzést!

    head -n <n> <file>: kiírja a <file> nevű fájl első <n> sorát. Ha az opcionális -n paraméter hiányzik, <n> értéke legyen 10. Építsen a cat parancs megoldására.\
    Ha a fájl nem létezik, adjon hibajelzést!

    tail -n <n> <file>: kiírja a <file> nevű fájl utolsó <n> sorát. Ha az opcionális -n paraméter hiányzik, <n> értéke legyen 10. Építsen a head parancs megoldására. Használja a List interfészt megvalósító LinkedListet!\
    Ha a fájl nem létezik, adjon hibajelzést!

    wc <file>: kiírja a <file> nevű fájl statisztikai adatait: sorok száma, szavak száma, betűk száma. Használja a String.split metódust! Induljon ki abból, hogy a cat parancsot hogyan valósította meg!\
    Ha a fájl nem létezik, adjon hibajelzést!

    grep <pattern> <file>: kiírja a <file> nevű fájl tartalmából a <pattern>-re illeszkedő sorokat. Használja a String.matches metódust! A minta elejére és végére helyezze el a „.*" karaktereket.\
    Ha a fájl nem létezik, adjon hibajelzést!

</code>
