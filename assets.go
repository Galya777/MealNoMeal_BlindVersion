package main

import (
	"embed"
	"io/fs"
	"path/filepath"
	"sync"

	"fyne.io/fyne/v2"
)

//go:embed images/*.jpg
var embeddedImagesFS embed.FS

var (
	embeddedImagesOnce sync.Once
	embeddedImagesMap  map[string]fyne.Resource
)

func getEmbeddedImageResource(filename string) fyne.Resource {
	embeddedImagesOnce.Do(func() {
		embeddedImagesMap = make(map[string]fyne.Resource)
		matches, err := fs.Glob(embeddedImagesFS, "images/*.jpg")
		if err != nil {
			return
		}
		for _, p := range matches {
			b, err := embeddedImagesFS.ReadFile(p)
			if err != nil {
				continue
			}
			name := filepath.Base(p)
			embeddedImagesMap[name] = fyne.NewStaticResource(name, b)
		}
	})

	if embeddedImagesMap == nil {
		return nil
	}
	return embeddedImagesMap[filename]
}
